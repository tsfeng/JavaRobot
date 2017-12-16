之前在项目中遇到一个问题，前端页面根据id(201708222017081820)到后台查询数据，正常情况下应该是没有问题的，因为id是后台传给前端的，但当时却查询不到数据；查找问题的过程中发现，后台给前端的id是201708222017081837，但前端传给后台的id却变成了201708222017081820。  
### **看一段代码**​  
```
<script type="text/javascript">
	var a1 = '201708222017081837';
	var a2 = 201708222017081837;
	console.log("a1=" + a1);
	console.log("a2=" + a2);
	console.log("typeof(a1) = " + typeof(a1));
	console.log("typeof(a2) = " + typeof(a2));
	console.log(Number.MAX_SAFE_INTEGER);
</script>
```
执行后，打印结果：  

    a1=201708222017081837
    a2=201708222017081820
    typeof(a1) = string
    typeof(a2) = number 
    9007199254740991
### **问题原因**​  
聪明如你肯定想到问题的原因所在了，没错，问题就出在Number上面。  
JavaScript使用的数字类型在内部表示为IEEE-754双精度浮点，像Java中的浮点数一样，它不能精确地表示所有数字，当一个数字大于Number.MAX_SAFE_INTEGER时，就有可能出现上面代码示例的情况。  