## DB TABLE 

### order table 
> 주문 이력을 저장하는 테이블

```sql
CREATE TABLE IF NOT EXISTS public.orders
(
    order_no character varying(50) NOT NULL,
    order_status character varying(20) NOT NULL,
    item character varying(20) NOT NULL,
    count integer,
    created_date timestamp(6) without time zone,
    modified_date timestamp(6) without time zone,
    CONSTRAINT orders_pkey PRIMARY KEY (order_no)
)
```

- 주문 상태 : PENDING, COMPLETE, FAILED

## stock table
> 재고를 관리하는 테이블
```sql
CREATE TABLE IF NOT EXISTS public.stock
(
    item character varying(20) NOT NULL,
    stock_count integer,
    status character varying(20) COLLATE pg_catalog."default",
    CONSTRAINT stock_pkey PRIMARY KEY (item)
)
```

- item 종류 : AMERICANO, LATTE, CAPPUCCINO
 


 
