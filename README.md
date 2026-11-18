# Simple Library System（图书管理系统）

> 一个用于检验 Java 语言基础的**控制台版**图书管理系统。零第三方依赖，JDK 11+ 可直接运行。

项目通过一个"图书管理"业务场景，把 Java SE 的核心知识点串起来：抽象类与继承、多态、集合（`ArrayList`）、文件 IO（`BufferedReader` / `BufferedWriter`）、字符串处理、异常处理、排序（冒泡 + `Comparator` + Lambda）、`UTF-8` 编码控制。

---

## 功能一览

| 菜单号 | 功能 | 对应方法 | 说明 |
|:---:|---|---|---|
| 1 | 添加图书 | `Main.addBookUI` → `BookManager.addBook` | 支持纸质书 / 电子书，按 ID 判重 |
| 2 | 显示所有图书 | `BookManager.showAllBooks` | 列表为空时提示"暂无图书" |
| 3 | 按书名查询 | `BookManager.findBookName` | 关键词模糊匹配（`contains`） |
| 4 | 借阅图书 | `BookManager.borrowBook` | 已借出时拒绝重复借阅 |
| 5 | 归还图书 | `BookManager.returnBook` | 未借出时提示无需归还 |
| 6 | 删除图书 | `BookManager.deleteBook` | 按 ID 删除并落盘 |
| 7 | 修改图书信息 | `BookManager.updateBook` | 可改书名、作者、出版社、价格 |
| 8 | 图书排序 | `sortByPrice` / `sortByBookName` | 价格升序、价格降序、书名升序 |
| 0 | 退出系统 | — | 关闭 `Scanner` 并结束程序 |

**图书类型**

- **纸质书 `PaperBook`**：额外字段为页数（`pageCount`）和馆藏位置（`location`）
- **电子书 `EBook`**：额外字段为文件大小（`fileSizeMB`）和下载地址（`downLoadUrl`）

---

## 环境要求

| 项 | 要求 |
|---|---|
| JDK | 11 及以上（`FileReader(File, Charset)` 自 JDK 11 提供；本机验证使用 JDK 17） |
| IDE | IntelliJ IDEA（可选，项目带 `.iml` 配置） |
| 编码 | 源码与数据文件统一 **UTF-8** |
| 依赖 | 无 |

---

## 快速开始

### 方式一：命令行

```bat
:: 在项目根目录执行（Windows CMD）
javac -encoding UTF-8 -d out src\*.java
java -cp out Main
```

PowerShell 下通配符不会自动展开，请改用：

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem src\*.java).FullName
java -cp out Main
```

> **务必在项目根目录下运行。** `BookManager` 用相对路径 `books.txt` 读写数据，工作目录不对会提示"未发现数据文件，从空白书库开始"。

### 方式二：IntelliJ IDEA

1. `File → Open` 选择项目根目录；
2. 确认 `Project Structure → Project SDK` 已配置 JDK；
3. 右键 `src/Main.java` → `Run 'Main.main()'`；
4. 检查 `Run/Debug Configurations → Working directory` 为 `$ProjectFileDir$`（这是默认值，不要改成 `src`）。

---

## 运行示例

数据文件 `books.txt` 中已有 1 本书时，启动并选择"2.显示所有图书"：

```text
已从文件加载1本图书。

===图书管理系统===
1.添加图书
2.显示所有图书
3.按书名查询
4.借阅图书
5.归还图书
6.删除图书
7.修改图书信息
8.图书排序
0.退出系统
请输入功能编号：2
=====图书列表（共1本）=====
图书编号：1
图书名称：java
作者：xym
出版社：程序员cbs
价格：12.0
类型：PAPER
状态：可借阅
页数:120
馆藏位置:A区12
------------------------
```

---

## 数据文件格式

`books.txt` 位于项目根目录，UTF-8 编码，**一行一本书，字段用 `|` 分隔，共 9 段**：

```text
PAPER|1|java|xym|程序员cbs|12.0|false|120|A区12
```

| 段序 | 字段 | 纸质书 PAPER | 电子书 EBOOK |
|:---:|---|---|---|
| 1 | 类型 | `PAPER`（必填，大写） | `EBOOK`（必填，大写） |
| 2 | 图书编号 | `int` | `int` |
| 3 | 书名 | 字符串 | 字符串 |
| 4 | 作者 | 字符串 | 字符串 |
| 5 | 出版社 | 字符串 | 字符串 |
| 6 | 价格 | `double` | `double` |
| 7 | 借阅状态 | `true` / `false` | `true` / `false` |
| 8 | 附加信息 1 | 页数 `int` | 文件大小 `double`（MB） |
| 9 | 附加信息 2 | 馆藏位置 | 下载地址 |

> **注意**：第 8、9 段实际由 `Book.getExtraData()` 用一个 `"|"` 拼成，例如 `return pageCount + "|" + location;`。因此新增图书类型时必须保证 `getExtraData()` 也返回"两段"结构，否则整行会被判定为格式异常并跳过。
>
> **类型值必须与 `getType()` 的返回值一致**（当前为 `PAPER` / `EBOOK`，全大写）。手改文件时写成 `PaperBook`、`Paper`、`paper` 都会导致该行被跳过。

---

## 目录结构

```text
Simple Library System/
├── src/
│   ├── Book.java            # 抽象基类：公共字段 + 抽象方法
│   ├── PaperBook.java       # 纸质书实现
│   ├── EBook.java           # 电子书实现
│   ├── BookManager.java     # 业务核心：CRUD、借还、排序、文件读写
│   └── Main.java            # 入口：菜单循环 + 控制台交互
├── books.txt                # 数据文件（运行时读写）
├── README.md                # 本文件
├── 开发文档.md               # 详细设计、流程与问题清单
└── Simple Library System.iml
```

---

## 常见问题（FAQ）

**Q1：books.txt 里明明有内容，启动后却显示"暂无图书，请先添加！"**

先看启动时控制台是否打印了 `未知图书类型，跳过：xxx` 或 `跳过格式异常的行：xxx`。

- 若打印"未知图书类型"：第一列的类型值不是 `PAPER` / `EBOOK`（大小写、拼写必须与 `getType()` 一致）。
- 若打印"格式异常"：该行不是 9 段，检查是否有字段里带了 `|`，或少了某一列。
- 若打印"未发现数据文件"：工作目录不在项目根，`books.txt` 没被找到。
- 若什么都没打印：`books.txt` 可能是空文件。

**Q2：数据突然全没了 / 只剩刚添加的一本**

`loadFromFile()` 一旦加载出 0 本（如上条原因），列表就是空的；此时再做**添加、借阅、归还、删除、排序**中任一操作，都会调用 `saveToFile()` 用空列表（或仅新数据）**覆盖整个 books.txt**。
处置：立即停止操作，从备份恢复。建议日常把 `books.txt` 纳入 Git 版本管理或定期复制。

**Q3：控制台中文显示乱码**

源码与 `books.txt` 都应以 UTF-8 保存；IDEA 中确认 `File → File Encodings` 的 Global/Project Encoding 为 UTF-8。命令行运行时加 `-encoding UTF-8` 编译参数。

---

## 相关文档

- [开发文档.md](./开发文档.md)：架构设计、类职责、持久化协议、核心流程走读、已知问题与改进路线。
