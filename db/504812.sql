/* Первый */

begin transaction isolation level serializable;
select sum(count) from products;
update products set count = 8 where name = 'product_1';

commit;

/* Тут ловим ошибку

ERROR:  could not serialize access due to read/write dependencies among transactions
DETAIL:  Reason code: Canceled on identification as a pivot, during commit attempt.
HINT:  The transaction might succeed if retried.

*/

/* Второй */


begin transaction isolation level serializable;
select sum(count) from products;
update products set count = 12 where name = 'product_3';
commit;

/* COMMIT */
