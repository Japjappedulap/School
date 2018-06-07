begin tran
waitfor delay '00:00:02'
insert into Types(Name)
values ('testing')
commit tran