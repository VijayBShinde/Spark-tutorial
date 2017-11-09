CMP1000,(Item(ITM01,Mouse,2,CMP1000),Company(CMP1000,Dell India,Mumbai)))
CMP1000,(Item(ITM02,Laptop,3,CMP1000),Company(CMP1000,Dell India,Mumbai)))
CMP1002,(Item(ITM03,Battery,1,CMP1002),Company(CMP1002,IBM India,Pune))))

case class Item(id:String,name:String,qty:Int,companyId:String)

val i1 = Item("ITM01","Mouse",2,"CMP1000")
val i2 = Item("ITM02","Laptop",1,"CMP1000")
val i3 = Item("ITM03","Battery",2,"CMP1001")

val items = sc.parallelize(List(i1,i2,i3))

val mappedItems = items.map(i => (i.companyId,i))

case class Company(id:String, name: String, city:String)

val c1 = Company("CMP1000", "Dell India", "Mumbai")
val c2 = Company("CMP1001", "IBM India", "Pune")
val c3 = Company("CMP1002", "HP India", "Delhi")

val companies = sc.parallelize(List(c1,c2,c3))

val mappedCompanies = companies.map(c => (c.id,c))

mappedItems.join(mappedCompanies).collect

Array((CMP1000,(Item(ITM01,Mouse,2,CMP1000),Company(CMP1000,Dell India,Mumbai))), 
      (CMP1000,(Item(ITM02,Laptop,1,CMP1000),Company(CMP1000,Dell India,Mumbai))), 
	  (CMP1001,(Item(ITM03,Battery,2,CMP1001),Company(CMP1001,IBM India,Pune))))
	  
mappedItems.leftOuterJoin(mappedCompanies).collect

Array((CMP1000,(Item(ITM01,Mouse,2,CMP1000),Some(Company(CMP1000,Dell India,Mumbai)))), 
	  (CMP1000,(Item(ITM02,Laptop,1,CMP1000),Some(Company(CMP1000,Dell India,Mumbai)))), 
	  (CMP1001,(Item(ITM03,Battery,2,CMP1001),Some(Company(CMP1001,IBM India,Pune)))))

mappedItems.rightOuterJoin(mappedCompanies).collect


Array((CMP1000,(Some(Item(ITM01,Mouse,2,CMP1000)),Company(CMP1000,Dell India,Mumbai))), 
       (CMP1000,(Some(Item(ITM02,Laptop,1,CMP1000)),Company(CMP1000,Dell India,Mumbai))), 
	   (CMP1001,(Some(Item(ITM03,Battery,2,CMP1001)),Company(CMP1001,IBM India,Pune))), 
	   (CMP1002,(None,Company(CMP1002,HP India,Delhi))))
	   
mappedItems.fullOuterJoin(mappedCompanies).collect


Array((CMP1000,(Some(Item(ITM01,Mouse,2,CMP1000)),Some(Company(CMP1000,Dell India,Mumbai)))),     	
       (CMP1000,(Some(Item(ITM02,Laptop,1,CMP1000)),Some(Company(CMP1000,Dell India,Mumbai)))), (CMP1001,(Some(Item(ITM03,Battery,2,CMP1001)),Some(Company(CMP1001,IBM India,Pune)))), 
	   (CMP1002,(None,Some(Company(CMP1002,HP India,Delhi)))))
