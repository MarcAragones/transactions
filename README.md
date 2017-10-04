# Transactions
This API collects transactions and returns statistics.

## API

### Add transaction

Store a new transaction.

```bash
POST /transactions
{
	"amount": 12.3,
	"timestamp": 1478192204000
}
```

#### Example

```bash
current_millis=`date +%s`"000"
curl -H "Content-Type: application/json" localhost:8080/transactions -d "{
  \"amount\": 1,
  \"timestamp\": ${current_millis}
}"
```

#### Response

Empty response. It return `201` if the transaction has been created and `204` if it's older than 60 seconds.

### Get statistics

This endpoint returns the statistics from the transactions of the last 60 seconds.

```bash
GET /statistics
```

#### Example

```bash
curl localhost:8080/statistics
```

#### Response

```json
{
	"sum":6.0,
	"avg":1.5,
	"max":2.0,
	"min":1.0,
	"count":4
}
```

## Fixed cost

In order to get a time cost of O(1), statistics are generated in real time and stored in a database. Then, it's only needed to read from the specific table.

When a new transaction is retrieved, it's stored in the database, its `amount` is added to the total, the `count` variable is increased, and `max` and `min` are updated if it's necessary.

The important point of the algorithm is the task scheduled 60 seconds after the transaction is retrieved. This task will be in charge of expiring the current transaction and the rollback. The class that performs the logic of scheduling the job is `TransactionRemoverImpl`.
