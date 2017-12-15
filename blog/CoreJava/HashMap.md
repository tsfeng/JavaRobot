```table
项目        |      价格    |    描述
尺子        |      ￥2      |   说明内容，也可为空
本子        |      ￥8     (描述偷懒不写完第三项也可以，不会破坏表格结构)   
Tip        |      ￥18    |  **光标在表格区域时，右键点击，有更简单的“编辑表格”命令**
```

 ``` table
 Method       |      Java8(-)    |    Java7(-)
 int size();        |  Y  |  Y
boolean isEmpty();       |  Y  |  Y
boolean containsKey(Object key);              |  Y  |  Y
boolean containsValue(Object value);        |  Y  |  Y
V get(Object key);       |  Y  |  Y
V put(K key, V value);       |  Y  |  Y
V remove(Object key);      |  Y  |  Y
void putAll(Map<? extends K, ? extends V> m);      |  Y  |  Y
void clear();      |      Y      |   Y
Set<K> keySet();       |  Y  |  Y
Collection<V> values();      |  Y  |  Y
Set<Map.Entry<K, V>> entrySet();       |  Y  |  Y
 int hashCode();       |  Y  |  Y
boolean equals(Object o);        |  Y  |  Y
```