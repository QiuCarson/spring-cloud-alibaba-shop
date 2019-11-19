## 概述

开发中。。。。

版本

spring boot 

## 主要技术栈

-  核心框架：Spring Boot + Spring Cloud Alibaba
-  ORM 框架：tk.mybatis 简化 MyBatis 开发
-  数据库连接池：Alibaba Druid
-  数据库缓存：Redis
-  消息中间件：RocketMQ
-  接口文档引擎：Swagger2 RESTful 风格 API 文档生成
-  全文检索引擎：ElasticSearch
-  系统网关：Spring Cloud Gateway
-  注册中心：Spring Cloud Alibaba Nacos Server
-  配置中心：Spring Cloud Alibaba Nacos Config 
-  熔断降级：Spring Cloud Alibaba Sentinel 
-  反向代理负载均衡：Nginx 

## 服务端口规划

| 服务名                                                       | 服务端口 | 服务说明   |
| ------------------------------------------------------------ | -------- | ---------- |
| [product-service](https://github.com/QiuCarson/spring-cloud-alibaba-shop/tree/master/shop-product-center/product-service) | 8101     | 产品服务   |
| [order-service](https://github.com/QiuCarson/spring-cloud-alibaba-shop/tree/master/shop-order-center/order-service) | 8201     | 订单服务   |
| [user-service](https://github.com/QiuCarson/spring-cloud-alibaba-shop/tree/master/shop-user-center/user-service) | 8301     | 用户服务   |
| shop-gateway                                                 | 8401     | 服务网关   |
| mail-service                                                 | 8501     | 邮件服务   |
| sms-service                                                  | 8601     | 短信服务   |
| cart-service                                                 | 8701     | 购物车服务 |
| pay-service                                                  | 8801     | 支付服务   |

应用端口规划

| 应用名   | 应用端口 | 应用说明               |
| -------- | -------- | ---------------------- |
| MySQL    | 3306     | 数据库                 |
| RocketMQ |          | 消息队列，分布式事务   |
| Nacos    |          | 服务注册发现，配置中心 |
| Sentinel |          | 熔断降级               |
| Nginx    | 80       | 反向代理               |
|          |          |                        |

