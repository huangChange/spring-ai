# Enterprise OpenClaw Platform (JDK 21 + Spring AI)

这是一个基于你提供 PRD 落地的 **企业级 OpenClaw 平台脚手架**，核心目标是：

- OpenClaw 仅作为执行引擎（Executor）
- 平台职责由 Control Plane / Task System / Tooling / Memory / LLM Gateway 承担
- 支持企业生产演进路径：单机 -> 异步 -> 多 Worker -> 多 Agent + RAG

## 当前实现范围（MVP+）

### 1. 异步任务系统
- `POST /api/v1/tasks`：创建任务并立即返回 `taskId`
- `GET /api/v1/tasks/{taskId}`：查询任务状态
- 状态机：`INIT -> DISPATCHED -> RUNNING -> SUCCESS/FAILED`

### 2. 调度与执行
- API 层创建任务后写入 Kafka Topic
- Worker 消费 Topic，调用 OpenClaw Executor
- 当前 Executor 使用 Spring AI `ChatClient` 作为默认实现

### 3. 企业可扩展骨架
- JDK 21
- Spring Boot + Spring AI + Kafka + Redis + Actuator
- 预留 tenant/session 上下文字段，满足多租户链路可扩展

## 目录说明

- `api/`：BFF 风格任务接口
- `service/`：任务编排与状态机
- `worker/`：消费执行引擎（OpenClaw）
- `infrastructure/`：Kafka Dispatch 等外部适配
- `domain/`：任务实体与状态模型

## 启动

```bash
mvn spring-boot:run
```

## 环境变量

- `OPENAI_API_KEY`
- `KAFKA_BOOTSTRAP_SERVERS`（默认 `localhost:9092`）
- `REDIS_HOST` / `REDIS_PORT`

## 下一步建议（与 PRD 对齐）

1. 增加 `Control Plane` 独立服务和流程编排 DSL（可参考 LangGraph 思想）
2. `TaskRepository` 切换为 Redis + MySQL 持久化实现
3. 增加 Tool Registry（动态注册 + RBAC 授权）
4. 增加 Memory 服务：Embedding + Vector Store + RAG Query
5. 增加 LLM Gateway：限流、降级、成本统计、审计
6. 增加 OpenTelemetry 全链路追踪与审计落库
