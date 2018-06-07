--set transaction isolation level read committed 
set transaction isolation level repeatable read


begin tran
select * from Colours
where id = 2586
waitfor delay '00:00:03'
select * from Colours
where id = 2586
commit tran