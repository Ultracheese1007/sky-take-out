#  🍱 sky-take-out

A full-featured **Spring Boot + MyBatis + Redis** based food delivery backend system.  
This project simulates a real-world take-out business workflow, covering user ordering, merchant order management, and operational data reporting.  
Developed and optimized by **Xinmei Ma (Leah)** as a backend engineering project.

---

## 🌟 Project Overview

**Sky Take-Out** is a modular backend system that supports both **user-side** and **merchant-side** operations, as well as **automated scheduled tasks** and **data analytics dashboards**.  
It is built following enterprise-level layered architecture with modules like:

- `sky-server`: main Spring Boot service
- `sky-common`: global exception handling, constants, and utility classes
- `sky-pojo`: entities, DTOs, and VOs used across modules

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend Framework | Spring Boot 2.7.x |
| ORM / Database | MyBatis + MySQL |
| Cache | Redis |
| API Documentation | Knife4j / Swagger |
| Build Tool | Maven |
| Task Scheduling | Spring Task (Scheduled Jobs) |
| Data Export | Apache POI (Excel) |
| Real-time Messaging | WebSocket |

---

## 🧩 Core Features

### 👤 User-Side Module
- **Order History Management** — query, cancel, and reorder previous orders  
- **Payment Flow** — support order creation and cancellation before payment  
- **Reorder Functionality** — quickly repeat a previous purchase  

### 🏪 Merchant-Side Module
- **Order Management Dashboard**
  - Accept / Reject Orders  
  - Cancel or Complete Orders  
  - Order Searching and Filtering  
- **Incoming Call & Urgent Order Alerts**
  - Real-time order push via **WebSocket**
- **Automatic Order Status Handling**
  - Scheduled tasks for timeout & payment expiration handling

### 📊 Data Analytics & Reporting
- **Turnover Statistics** — compute daily and total revenue  
- **User Growth Report** — new vs. total user count per day  
- **Order Completion Metrics** — total vs. valid orders with completion rate  
- **Sales Top 10 Ranking** — list top-selling dishes by order volume  
- **Export Business Reports** — export 30-day operational data to Excel  
  (based on a predefined template with Apache POI)

### 🖥️ Workspace Dashboard
- Display real-time operational overview  
- Integrate multiple KPIs: revenue, new users, unit price, and order completion rate

---

## 🧠 Implementation Highlights

- **Layered Architecture (Controller → Service → Mapper)**
- **Global Exception & Result Wrappers**
- **Aspect-Oriented AutoFill (created/updated time & user)**
- **Redis for caching & concurrency control**
- **Scheduled Jobs for payment timeout and daily statistics**
- **POI streaming export for large Excel files**
- **WebSocket real-time push for order notifications**

---

## 🧰 Example Commit History (Functional Milestones)

| Commit | Description |
|--------|--------------|
| `feat(order): implement user order history module` | Add history query, cancel order, and reorder functions |
| `feat(merchant): add order management & status update` | Enable order acceptance, rejection, and completion |
| `feat(task): implement scheduled order status processing` | Auto-handle payment timeout & expired orders |
| `feat(websocket): add order reminder via WebSocket` | Enable real-time merchant notifications |
| `feat(report): add turnover and user statistics analysis` | Daily data aggregation & business insights |
| `feat(report): export business operation report to Excel` | 30-day data export with POI template |

---

## 🚀 How to Run

1. **Clone Repository**
   ```bash
   git clone https://github.com/Ultracheese1007/sky-take-out.git
   cd sky-take-out/sky-server
   ```
2. **Setup Database**

- Import sky.sql into MySQL

- Update database configs in application-dev.yml

3. **Start Redis**
   ```bash
   redis-server
   ```

4. **Run Spring Boot**
   ```bash
   mvn spring-boot:run
   ```
   - or launch SkyApplication directly in IntelliJ IDEA.

5. **Access API Docs**

http://localhost:8080/doc.html
(Knife4j Swagger UI)
