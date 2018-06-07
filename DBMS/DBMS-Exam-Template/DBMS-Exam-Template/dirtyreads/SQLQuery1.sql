
begin Transaction
update Colours set name='Red'
where id = 2586
waitfor delay '00:00:03'
rollback transaction