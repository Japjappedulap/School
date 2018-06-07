--SET DEADLOCK_PRIORITY HIGH

begin tran
update Colours set name='1' where id = 3589
waitfor delay '00:00:03'
update Types set name='1' where id = 1105
commit tran