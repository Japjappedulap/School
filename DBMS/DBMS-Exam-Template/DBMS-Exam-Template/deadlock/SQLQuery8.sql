SET DEADLOCK_PRIORITY HIGH
begin tran
update Types set name='2' where id = 1105
waitfor delay '00:00:03'
update Colours set name='2' where id = 3589

commit tran