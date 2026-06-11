# 容器

## 编译与运行
```bash
# 编译主程序
mvn compile
# 编译测试程序
mvn test-compile
# 运行测试
mvn test
# 运行主程序
java -cp target/classes com.example.Main
```

## Task1
源代码：[WordAnalyzer](src/main/java/com/example/WordAnalyzer.java)

代码片段：
```java
private void loadFile(String filePath) throws IOException {
    FileInputStream fis = new FileInputStream(filePath);
    Scanner scanner = new Scanner(fis);
    while (scanner.hasNext()) {
        words.add(scanner.next());
    }
    scanner.close();
    fis.close();
}
```

## Task2
源代码：[WordAnalyzer](src/main/java/com/example/WordAnalyzer.java)

代码片段：
```java
public int getDistinctWordCount() {
    return wordFrequency.size();
}
```

## Task3
源代码：[WordAnalyzer](src/main/java/com/example/WordAnalyzer.java)

代码片段：
```java
private void calculateWordFrequency() {
    for (String word : words) {
        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
    }
}

public Integer getWordFrequency(String word) {
    return wordFrequency.get(word);
}
```

测试代码：[SparseMatrixTest](src/test/java/com/example/SparseMatrixTest.java)

运行测试：

```bash
# 编译测试代码
mvn test-compile
# 运行测试
mvn test -Dtest="com.example.SparseMatrixTest"
```

## Task4
源代码：[SparseMatrix](src/main/java/com/example/SparseMatrix.java)

测试代码：[WordAnalyzerTest](src/test/java/com/example/WordAnalyzerTest.java)

运行测试：
```bash
# 编译测试代码
mvn test-compile
# 运行测试
mvn test -Dtest="com.example.WordAnalyzerTest"
```