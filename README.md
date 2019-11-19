## 商品API

开发中。。。。

## 服务端口规划

| 服务名                                                       | 服务端口 | 服务说明 |
| ------------------------------------------------------------ | -------- | -------- |
| [product-service](https://github.com/QiuCarson/spring-cloud-alibaba-shop/tree/master/shop-product-center/product-service) | 8101     | 产品服务 |
| [order-service](https://github.com/QiuCarson/spring-cloud-alibaba-shop/tree/master/shop-order-center/order-service) | 8201     | 订单服务 |
| [user-service](https://github.com/QiuCarson/spring-cloud-alibaba-shop/tree/master/shop-user-center/user-service) | 8301     | 用户服务 |
| shop-gateway                                                 | 8401     | 服务网关 |
|                                                              |          |          |

应用端口规划

| 应用名   | 应用端口 | 应用说明               |
| -------- | -------- | ---------------------- |
| MySQL    | 3306     | 数据库                 |
| RocketMQ |          | 消息队列，分布式事务   |
| Nacos    |          | 服务注册发现，配置中心 |
| Sentinel |          | 熔断降级               |
| Nginx    | 80       | 反向代理               |
|          |          |                        |

