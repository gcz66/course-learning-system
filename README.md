# 课程学习系统后端（学校项目）

为课程学习小程序提供 RESTful 后端，覆盖用户登录注册、课程管理、章节学习、班级绑定、成绩计算等模块。

## 技术栈

| 层 | 技术 |
| --- | --- |
| 框架 | Spring Boot 3.2.5 + Spring Data JPA + Hibernate |
| 数据库 | MySQL 8（JPA 自动建表，无需手动执行 SQL） |
| 鉴权 | JWT + 登录拦截器 |
| 密码 | BCrypt 加密 |
| 返回 | 统一 `Result<T>` + 全局异常处理 |

## 快速开始

### 1. 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS course_learning DEFAULT CHARACTER SET utf8mb4;
```

> 表结构由 JPA + Hibernate 在启动时自动创建/更新（`ddl-auto: update`），无需执行建表脚本。

### 2. 修改配置并启动

```yaml
# src/main/resources/application.yml 中修改数据库账号密码（默认 root/123456）
```

```bash
cd course-learning-system
mvn spring-boot:run
# 服务地址：http://localhost:8081
```

### 3. 自测接口

```bash
# 注册
curl -X POST http://localhost:8081/api/user/register -H "Content-Type: application/json" -d '{"username":"stu001","password":"123456","nickname":"小明"}'

# 登录（返回 token）
curl -X POST http://localhost:8081/api/user/login -H "Content-Type: application/json" -d '{"username":"stu001","password":"123456"}'

# 微信小程序登录（wx_openid 静默注册/登录）
curl -X POST http://localhost:8081/api/user/wx-login -H "Content-Type: application/json" -d '{"wxOpenid":"xxx","nickname":"微信用户"}'
```

## API 一览

| 模块 | 接口 | 说明 | 鉴权 |
| --- | --- | --- | --- |
| 用户 | `POST /api/user/register` | 账号密码注册 | 公开 |
| 用户 | `POST /api/user/login` | 账号密码登录 | 公开 |
| 用户 | `POST /api/user/wx-login` | 微信 openid 登录（自动注册） | 公开 |
| 用户 | `GET /api/user/info` | 当前用户信息 | 需登录 |
| 用户 | `POST /api/user/bind-class` | 绑定班级 | 需登录 |
| 课程 | `GET /api/course/list?category=&keyword=` | 课程列表 | 公开 |
| 课程 | `GET /api/course/{id}` | 课程详情 | 公开 |
| 课程 | `POST /api/course` | 创建课程 | 需登录 |
| 课程 | `PUT /api/course/{id}` | 更新课程 | 需登录 |
| 课程 | `DELETE /api/course/{id}` | 删除课程（级联删章节） | 需登录 |
| 章节 | `GET /api/chapter/list/{courseId}` | 章节列表 | 公开 |
| 章节 | `POST /api/chapter` | 添加章节 | 需登录 |
| 章节 | `PUT /api/chapter/{id}` | 更新章节 | 需登录 |
| 章节 | `DELETE /api/chapter/{id}` | 删除章节 | 需登录 |
| 学习 | `POST /api/study/report` | 上报学习进度 | 需登录 |
| 学习 | `GET /api/study/course/{courseId}` | 某课程学习记录 | 需登录 |
| 学习 | `GET /api/study/mine` | 我的学习记录 | 需登录 |
| 学习 | `GET /api/study/progress/{courseId}` | 课程学习进度(%) | 需登录 |
| 班级 | `GET /api/class/list` | 班级列表 | 公开 |
| 班级 | `POST /api/class` | 创建班级 | 需登录 |
| 班级 | `PUT /api/class/{id}` | 更新班级 | 需登录 |
| 班级 | `DELETE /api/class/{id}` | 删除班级（有学生时拒绝） | 需登录 |
| 班级 | `GET /api/class/{id}/students` | 班级学生列表 | 需登录 |
| 成绩 | `POST /api/score` | 录入/更新成绩 | 需登录 |
| 成绩 | `GET /api/score/user/{userId}` | 学生全部成绩 | 需登录 |
| 成绩 | `GET /api/score/course/{courseId}` | 课程全部成绩 | 需登录 |
| 成绩 | `GET /api/score/stats?userId=&courseId=` | 成绩统计(平均分) | 需登录 |

## 核心设计

- **微信登录**：用户表 `t_user` 设计 `wx_openid` 唯一字段，微信端通过 openid 静默注册并登录，无需密码
- **自动建表**：基于 JPA + Hibernate `ddl-auto: update` 自动维护表结构（t_user / t_class / t_course / t_chapter / t_study_record / t_score 共 6 张表）
- **JWT 鉴权**：登录签发 token，拦截器解析校验，用户 id 写入 request 供业务使用；无 token / 过期返回 401
- **统一返回**：`Result<T>` 统一响应体，`GlobalExceptionHandler` 处理业务异常、参数校验、兜底异常
- **级联删除**：删除课程时级联删除其章节；删除班级时若仍有学生绑定则拒绝
- **学习进度**：按章节上报进度（0-100），课程进度 = 已完成章节 / 总章节
- **成绩计算**：按"学生+课程+考试"维度录入，重复录入自动更新；支持平均分统计

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
