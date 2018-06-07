set transaction isolation level repeatable read
--set transaction isolation level serializable

begin tran
select * from Types
waitfor delay '00:00:04'
select * from Types
commit tran