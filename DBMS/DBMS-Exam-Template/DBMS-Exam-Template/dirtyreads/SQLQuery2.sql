--set transaction isolation level read uncommitted  
set transaction isolation level read committed

begin tran
select * from Colours
where id = 2586
waitfor delay '00:00:05'
select * from Colours
where id = 2586
commit tran