# 容器

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
```

