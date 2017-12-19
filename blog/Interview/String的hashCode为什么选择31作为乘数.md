在了解String的hashCode方法选择数字31作为乘子（类似的还有AbstractList）的原因之前，先来看看String的hashCode方法是怎样实现的，如下：
```
public int hashCode() {
    int h = hash;
    if (h == 0 && value.length > 0) {
        char val[] = value;
        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];
        }
        hash = h;
    }
    return h;
}
```
我们可以由上面的for循环推导出一个计算公式，hashCode方法注释中已经给出。如下：
```
s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
```
这里说明一下，上面的s数组即源码中的val数组，是String内部维护的一个char类型数组。**空字符串的hashCode默认为0。**  
接下来看本文的重点，即选择31的理由。从网上的资料来看，一般有如下两个原因：  
**第一，31是一个不大不小的质数，是作为hashCode乘数的优选质数之一。**  
另外一些相近的质数，比如29、37、41等等，也都是不错的选择。那么为啥偏偏选中了31呢？请看第二个原因。  
**第二、31可以被JVM优化，31 * i = (i << 5) - i。**  

上面两个原因中，第一个需要解释一下，第二个比较简单，就不说了。下面我来解释第一个理由。一般在设计哈希算法时，会选择一个特殊的质数。至于为啥选择质数，我想应该是可以降低哈希算法的冲突率。至于原因，这个就要问数学家了，我几乎可以忽略的数学水平解释不了这个原因。上面说到，31是一个不大不小的质数，是优选乘子。那为啥同是质数的2和101（或者更大的质数）就不是优选乘子呢，分析如下。  

这里先分析质数2。首先，假设n = 6，然后把质数2和n带入上面的计算公式。并仅计算公式中次数最高的那一项，结果是2^5 = 32，是不是很小。所以这里可以断定，当字符串长度不是很长时，用质数2做为乘子算出的哈希值，数值不会很大。也就是说，哈希值会分布在一个较小的数值区间内，分布性不佳，最终可能会导致冲突率上升。  

上面说了，质数2做为乘子会导致哈希值分布在一个较小区间内，那么如果用一个较大的大质数101会产生什么样的结果呢？根据上面的分析，我想大家应该可以猜出结果了。就是不用再担心哈希值会分布在一个小的区间内了，因为101^5 = 10,510,100,501。但是要注意的是，这个计算结果太大了。如果用int类型表示哈希值，结果会溢出，最终导致数值信息丢失。尽管数值信息丢失并不一定会导致冲突率上升，但是我们暂且先认为质数101（或者更大的质数）也不是很好的选择。最后，我们再来看看质数31的计算结果：31^5 = 28629151，结果值相对于32和10,510,100,501来说。是不是很nice，不大不小。  

上面用了比较简陋的数学手段证明了数字31是一个不大不小的质数，是作为 hashCode 乘子的优选质数之一。接下来我会用详细的实验来验证上面的结论，不过在验证前，我们先看看Stack Overflow上关于这个问题的讨论，Why does Java's hashCode() in String use 31 as a multiplier?。  
其中排名第一的答案引用了《Effective Java》中的一段话，这里也引用一下：  
> The value 31 was chosen because it is an odd prime. If it were even and the multiplication overflowed, information would be lost, as multiplication by 2 is equivalent to shifting. The advantage of using a prime is less clear, but it is traditional. A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction for better performance: 31 * i == (i << 5) - i. Modern VMs do this sort of optimization automatically.

简单翻译一下：

> 选择数字31是因为它是一个奇质数，如果选择一个偶数会在乘法运算中产生溢出，导致数值信息丢失，因为乘二相当于移位运算。选择质数的优势并不是特别的明显，但这是一个传统。同时，数字31有一个很好的特性，即乘法运算可以被移位和减法运算取代，来获取更好的性能：31 * i == (i << 5) - i，现代的 Java 虚拟机可以自动的完成这个优化。

排名第二的答案设这样说的：
> As Goodrich and Tamassia point out, If you take over 50,000 English words (formed as the union of the word lists provided in two variants of Unix), using the constants 31, 33, 37, 39, and 41 will produce less than 7 collisions in each case. Knowing this, it should come as no surprise that many Java implementations choose one of these constants.

这段话也翻译一下：
> 正如Goodrich和Tamassia指出的那样，如果你对超过50,000个英文单词（由两个不同版本的Unix字典合并而成）进行hash code运算，并使用常数31, 33, 37, 39和41作为乘子，每个常数算出的哈希值冲突数都小于7个，所以在上面几个常数中，常数31被Java实现所选用也就不足为奇了。

上面的两个答案完美的解释了Java源码中选用数字31的原因。
