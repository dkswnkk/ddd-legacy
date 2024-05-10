# 키친포스

## 퀵 스타트

```sh
cd docker
docker compose -p kitchenpos up -d
```

## 요구 사항

### 상품(product)

- [x] 각 상품은 **상품명**과 **가격**을 필수로 가진다.
    - 상품의 가격은 **비어있지 않고, 0원 이상이어야 한다.**
    - 상품명은 **비어있거나 욕설을 포함할 수 없다.** 욕설의 포함 여부는 **외부에 요청해서 검증**한다.

  #### 상품 생성
    - 상품의 가격은 **비어있지 않고, 0원 이상이어야 한다.**
    - 상품명은 **비어있거나 욕설을 포함하지 않아야한다.**.

  #### 상품 가격 변경
    - 변경하려는 가격은 **0원 이상이어야 한다.**
    - 상품이 속해있는 메뉴 가격과 메뉴에 속해있는 모든 상품들의 가격 합계를 비교하여, 만약 메뉴의 가격이 속해있는 상품들의 **총 합계 가격보다 높을 경우** 자동으로 메뉴를 **숨김 상태로 설정**한다.

### 메뉴(menu)

- [x] 각 메뉴는 **메뉴명, 가격**, 그리고 **노출 여부**을 필수로 가진다.
    - 메뉴명은 **비어있거나 욕설을 포함할 수 없다.** 욕설의 포함 여부는 **외부에 요청해서 검증**한다.
    - 각 메뉴의 가격은 **비어있지 않고, 0원 이상이어야 한다.**
    - 메뉴의 가격은 **현재 메뉴에 포함된 상품들의 총합 가격보다 같거나 낮아야 한다.**
    - 메뉴 등록, 가격 변경 그리고 노출로 메뉴 변경 시, 요청 가격은 **현재 메뉴에 포함된 상품들의 총합 가격보다 같거나 낮아야 한다.**
- [x] 각 메뉴는 소속된 **메뉴 그룹의 ID**를 참조한다.

  #### 메뉴 생성
    - 메뉴명이 **비어있지 않으며 욕설을 포함하지 않아야 한다.**
    - 생성하려는 메뉴의 가격은 **0원 이상이어야 한다.**
    - 생성하려는 메뉴의 가격이 메뉴에 포함된 상품들의 총합 가격보다 **같거나 낮아야 한다.**
    - 메뉴를 생성할 때는 먼저 **메뉴에 해당하는 메뉴 그룹이 존재해야 한다.**
    - 메뉴에 속한 상품의 갯수가 **0개 미만일 경우 생성할 수 없다.**

  #### 메뉴 가격 변경
    - 메뉴의 새로운 가격이 **비어있지 않고, 0원 이상**일 때에만 변경 가능하다.
    - 변경 하고자 하는 메뉴 가격이 **현재 메뉴에 포함된 상품들의 총합 가격보다 같거나 낮아야 한다.**

  #### 메뉴 노출
    - 메뉴의 가격이 **현재 메뉴에 포함된 상품들의 총합 가격보다 같거나 낮은 경우**에만 메뉴를 **노출 상태로 변경**할 수 있다.

  #### 메뉴 숨김
    - 메뉴를 **숨김 상태로 변경**할 때는 메뉴 노출 때와 달리 상품들의 총합 가격을 검증하지 않는다.

### 메뉴 상품(menu_product)

- [x] 메뉴 상품은 **상품 ID**와 **메뉴 ID**를 필수로 가진다.
- [x] 각 메뉴 상품은 **수량** 정보를 포함한다.

### 메뉴 그룹(menu_group)

- [x] 각 메뉴 그룹은 **메뉴 그룹명**을 필수로 가진다.
    - 메뉴 그룹명은 **비어있을 수 없다.**

  #### 메뉴 그룹 생성
    - 메뉴 그룹명이 **비어있을 때는 생성할 수 없다.**

### 주문(orders)

- [x] 주문은 선택적으로 **배달 주소**를 가질 수 있다.
- [x] 각 주문은 **주문 시간**을 기록한다.
- [x] 주문은 **주문 방법**을 가진다.
    - 주문 방법은 `DELIVERY, TAKEOUT, EAT_IN` 중 하나이다.
- [x] 각 주문은 현재의 **진행 상태**를 나타낸다.
    - 진행 상태는 `WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED` 중 하나이다.
    - 주문 진행 상태는 주문 방법에 따라 플로우와 진행 상태가 달라진다.
    - 배달 주문(`DELIVERY`)은 `WAITING`상태에서 시작하여,`ACCEPTED`,`SERVER`,`DELIVERING`,`DELIVERED`를 거쳐`COMPLETED`로 마무리된다.
    - 테이크 아웃(`TAKEOUT`)은 `WAITING`상태에서 시작하여,`ACCEPTED`,`SERVER`단계를 거쳐 직접 고객에게 제공된 후`COMPLETED`로 마무리된다.
    - 매장 내 식사(`EAT_IN`)은 `WAITING`상태에서 시작하여,`ACCEPTED`,`SERVER`단계를 거쳐 모든 서비스가 제공된 후`COMPLETED`로 마무리된다.
- [x] 주문은 선택적으로 **주문 테이블의 ID**를 가질 수 있다.
    - 주문이 주문 테이블의 ID를 가지는 경우는 주문 방법이 `EAT_IN` 즉 **매장 내 식사일 경우**일 때이다.

  #### 주문 생성
    - 주문 생성 시 **주문 방법**이 지정되어 있어야 한다.
    - 주문 내역은 **비어있을 수 없다.**
    - 매장 내 식사(`EAT_IN`)를 제외한 모든 주문 타입에서는 주문한 메뉴의 수량이 **0 이상**이어야 한다.
    - 각 메뉴 항목은 **메뉴가 노출 상태**이어야 하며, 메뉴 가격이 주문내역 항목 가격과 일치해야 한다.
    - **배달 주소**는 배달 주문(`Delivery`) 시 필수이다.
    - 매장 내 식사(`EAT_IN`)의 경우, 해당 **주문 테이블이 사용 중**이어야 한다.

  #### 주문 수락
    - 주문은 `WAITING` 상태에서만 수락할 수 있다.
    - 배달 주문은 배달 요청이 가능해야 하며, 주문 금액과 배달 주소가 필요하다.
    - 주문 배달의 경우 **라이더를 외부 제품을 사용해서 호출**한다.

  #### 제공
    - 주문은 `ACCEPTED` 상태에서만 제공할 수 있다.
    - 주문 상태를 `SERVED`로 변경한다.

  #### 배달 시작
    - 배달 주문은 `SERVED` 상태와 주문 방법이 `DELIVERY`일 경우에만 배달을 시작할 수 있다.
    - 주문 상태를 `DELIVERING`으로 변경한다.

  #### 배달 완료
    - 주문은 `DELIVERING` 상태에서만 배달 완료로 처리할 수 있다.
    - 주문 상태를 `DELIVERED`로 변경한다.

  #### 주문 완료
    - 배달 주문은 `DELIVERED` 상태에서만 주문 완료로 처리할 수 있다.
    - 테이크아웃 또는 매장 내 식사는 `SERVED` 상태에서만 주문 완료로 처리할 수 있다.
    - 매장 내 식사의 경우 주문이 완료되면 주문 테이블을 치운다.
    - 테이블을 치울 때는 해당 테이블의 모든 주문이 완료된 상태여야만 가능하다.
    - 주문 상태를 `COMPLETED`로 변경한다.

### 주문 테이블(order_table)

- [ ] 각 주문 테이블은 **테이블의 이름**을 필수로 가진다.
    - 테이블의 이름은 **비어있을 수 없다.**
- [ ] 각 테이블은 **현재 테이블의 사용 여부**를 나타내는 **상태값**을 필수로 가진다.
    - 테이블은 처음에는 사용 중이지 않은 상태(false)로 생성된다.
    - 손님이 앉으면 상태가 사용 중 상태(true)로 변경된다.
- [ ] 각 테이블은 **앉아 있는 손님 수**를 필수로 기록한다.
    - 초기에는 **0명**으로 설정된다.
    - 손님 수는 **0명 이상**이어야 하며, 테이블이 비어있을 때(false)는 수정할 수 없다.

  #### 주문 테이블 생성
    - 테이블의 이름은 **비어있을 수 없다.**
    - 테이블은 처음에는 착석한 인원 **0명**, 그리고 사용 중이지 않은 상태(false)로 생성된다.

  #### 테이블 착석
    - 테이블에 착석하면 테이블을 사용 중 상태(true)로 변경한다.

  #### 테이블 치움
    - 주문이 완료된 상태(`COMPLETED`)가 아니라면 테이블을 치울 수 없다.
    - 테이블을 치우면 착석한 인원 **0명**, 그리고 사용 중이지 않은 상태(false)로 변경한다.

  #### 테이블에 착석한 인원 수 변경
    - 테이블에 착석하는 인원은 **0명 이상**이어야 한다.
    - 테이블이 사용 중 상태(true)가 아니라면 변경할 수 없다.

### 주문 내역 항목(order_line_item)

- [ ] 각 주문 내역 항목(주문 내역)은 **메뉴 ID**와 연결되어 어떤 메뉴가 주문되었는지를 식별한다.
- [ ] 각 주문 내역 항목(주문 내역)은 **주문 ID**를 참조하여 해당 주문 항목이 속한 주문을 식별한다.

## 용어 사전

### 도메인 모델 용어

### 주문 상태(OrderStatus)

| 값          | 설명                        |
|------------|---------------------------|
| WAITING    | 주문이 접수되었으나 아직 처리되지 않은 상태. |
| ACCEPTED   | 주문이 확인되어 준비가 시작된 상태.      |
| SERVED     | 주문이 준비되어 제공된 상태.          |
| DELIVERING | 주문이 배달 중인 상태.             |
| DELIVERED  | 주문이 고객에게 배달된 상태.          |
| COMPLETED  | 주문이 완료된 상태.               |

### 주문 방법(OrderType)

| 값        | 설명                    |
|----------|-----------------------|
| DELIVERY | 주문이 배달로 처리되는 경우.      |
| TAKEOUT  | 주문이 테이크아웃으로 처리되는 경우.  |
| EAT_IN   | 주문이 매장 내 식사로 처리되는 경우. |

## 모델링

<img width="453" alt="image" src="https://github.com/dkswnkk/ddd-legacy/assets/74492426/f150d536-e9ea-4a01-aa33-9394339cd51f">

