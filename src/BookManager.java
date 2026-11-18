import java.util.ArrayList;
import java.util.Comparator;
import java.nio.charset.StandardCharsets;
import java.io.*;
public class BookManager {
    private ArrayList<Book> booklist = new ArrayList<>();
    private static final String FILE_PATH = "books.txt";
/**
 * BookManager类的构造函数
 * 在创建BookManager对象时自动调用loadFromFile()方法
 * 用于初始化时从文件加载书籍数据
 */
    public BookManager(){
        loadFromFile(); // 调用loadFromFile()方法从文件加载书籍数据
    }
/**
 * 从文件加载图书数据的方法
 * 该方法会读取指定路径下的文件，解析每一行数据并创建相应的图书对象
 * 支持纸质书(PAPER)和电子书(EBOOK)两种类型
 */
    private void loadFromFile() {
        // 创建文件对象，使用FILE_PATH常量指定的路径
        File file = new File(FILE_PATH);
        // 检查文件是否存在，如果不存在则提示并返回
        if (!file.exists()) {
            System.out.println("未发现数据文件，从空白书库开始");
            return;
        }
        // 使用try-with-resources语句自动关闭资源，指定UTF-8字符编码
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line; // 用于存储读取的每一行数据
            // 循环读取文件直到末尾
            while ((line = reader.readLine()) != null) {
                // 跳过空行
                if (line.trim().isEmpty()) {
                    continue;
                }
                // 使用"|"作为分隔符分割每行数据，-1表示保留所有空字符串
                String[] parts = line.split("\\|", -1);
                // 检查数据格式是否正确，每行应有9个部分
                if (parts.length != 9) {
                    System.out.println("跳过格式异常的行：" + line);
                    continue;
                }
                try {

                    // 解析图书的基本信息
                    String type = parts[0]; // 图书类型
                    int id = Integer.parseInt(parts[1]); // 图书ID
                    String bookName = parts[2]; // 图书名称
                    String author = parts[3]; // 作者
                    String publisher = parts[4]; // 出版社
                    Double price = Double.parseDouble(parts[5]); // 价格
                    boolean isBorrowwed = Boolean.parseBoolean(parts[6]); // 借阅状态
                    Book book; // 声明图书对象

                    // 根据图书类型创建不同的图书对象
                    if ("PAPER".equals(type)) {

                        // 创建纸质书对象
                        int pageCount = Integer.parseInt(parts[7]); // 页数
                        String location = parts[8]; // 存放位置
                        book = new PaperBook(id, bookName, author, publisher, price, isBorrowwed, pageCount, location);
                    } else if ("EBOOK".equals(type)) {

                        // 创建电子书对象
                        double fileSize = Double.parseDouble(parts[7]); // 文件大小
                        String url = parts[8]; // 下载链接
                        book = new EBook(id, bookName, author, publisher, price, isBorrowwed, fileSize, url);
                    } else {
                        // 未知图书类型，跳过该行
                        System.out.println("未知图书类型，跳过：" + line);
                        continue;
                    }
                    // 将创建的图书对象添加到图书列表中
                    booklist.add(book);
                } catch (NumberFormatException e) {
                    // 处理数字格式异常，跳过数据损坏的行
                    System.out.println("跳过数据损坏的行：" + line);
                }
            }
            // 打印加载成功的图书数量
            System.out.println("已从文件加载" + booklist.size() + "本图书。");
        } catch (IOException e) {
            // 处理IO异常，打印错误信息
            System.out.println("读取文件失败：" + e.getMessage());
        }
    }

/**
 * 将图书列表数据保存到文件中的方法
 * 使用BufferedWriter进行高效的文件写入操作
 * 采用UTF-8编码确保中文正常显示
 * 每本书的信息用"|"分隔，每行代表一本书的信息
 */
    private void saveToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH,StandardCharsets.UTF_8))){
            // 遍历图书列表，将每本书的信息写入文件
            for(Book book:booklist){
                // 将图书的各个属性用"|"连接成字符串并写入文件
                // 属性包括：类型、ID、书名、作者、出版社、价格、借阅状态、额外信息
                writer.write(book.getType()+"|"+ book.getId()+"|"+ book.getBookName()+"|"+ book.getAuthor()
                        +"|"+ book.getPublisher()+"|"+ book.getPrice()+"|"+ book.isBorrowed()+"|"+ book.getExtraData());
                // 写入换行符，准备写入下一本书的信息
                writer.newLine();
            }
        }catch (IOException e){
            // 捕获并处理IO异常，打印错误信息
            System.out.println("保存数据失败："+e.getMessage());
        }
    }

/**
 * 根据图书ID查找图书
 * @param id 要查找的图书ID
 * @return 如果找到返回对应的Book对象，否则返回null
 */
    public Book findById(int id){
    // 遍历图书列表
        for(Book b:booklist){
        // 检查当前图书的ID是否与要查找的ID匹配
            if(b.getId() == id){
            // 如果找到匹配的图书，直接返回该图书对象
                return b;
            }
        }
    // 如果遍历完整个列表都没有找到匹配的图书，返回null
        return null;
    }

/**
 * 添加图书的方法
 * @param book 要添加的图书对象
 * @return 添加成功返回true，添加失败返回false
 */
    public boolean addBook(Book book){
    // 检查图书是否已存在，通过图书ID进行查找
        if(findById(book.getId())!=null){
        // 如果图书已存在，打印失败信息并返回false
            System.out.println("添加失败：编号"+book.getId()+"的图书已存在！");
            return false;
        }
    // 如果图书不存在，将图书添加到图书列表中
        booklist.add(book);
    // 打印添加成功的信息，包含图书名称
        System.out.println("添加成功："+book.getBookName());
    // 保存图书信息到文件
        saveToFile();
    // 返回true表示添加成功
        return true;
    }

/**
 * 根据图书ID删除图书的方法
 * @param id 要删除的图书ID
 */
    public void deleteBook(int id){
    // 根据ID查找图书
        Book b = findById(id);
    // 判断图书是否存在
        if(b == null){
        // 如果图书不存在，输出提示信息并返回
            System.out.println("编号"+id+"的图书不存在，无法删除！");
            return;
        }
    // 从图书列表中移除该图书
        booklist.remove(b);
    // 输出删除成功的提示信息，包含图书名称
        System.out.println("删除成功，已删除：《"+b.getBookName()+"》");
    // 保存更新后的图书列表到文件
        saveToFile();
    }

/**
 * 更新图书信息的方法
 * @param id 图书ID
 * @param bookName 图书名称
 * @param author 作者
 * @param publishere 出版社
 * @param price 价格
 * @return 更新成功返回true，失败返回false
 */
    public boolean updateBook(int id,String bookName,String author,String publishere,double price){
        // 根据ID查找图书
        Book b = findById(id);
        // 如果图书不存在，返回false
        if(b == null){
            return  false;
        }
        // 设置图书的各个属性
        b.setBookName(bookName);
        b.setAuthor(author);
        b.setPublisher(publishere);
        b.setPrice(price);
        // 将更新后的图书信息保存到文件
        saveToFile();
        // 返回true表示更新成功
        return true;
    }

/**
 * 根据关键词查找图书名称
 * @param keyword 要搜索的关键词
 */
    public void findBookName(String keyword){
    // 初始化一个标记变量，用于是否找到匹配的图书
        boolean found = false;
    // 遍历图书列表
        for(Book b:booklist){
        // 检查当前图书的名称是否包含关键词
             if(b.getBookName().contains(keyword)){
            // 打印匹配的图书信息
                 System.out.println(b);
            // 打印分隔线，提高输出可读性
                 System.out.println("------------------------");
            // 设置找到标记为true
                 found = true;
             }
        }
    // 如果没有找到任何匹配的图书
        if(!found){
        // 输出未找到图书的提示信息
            System.out.println("没有找到书名包括《"+keyword+"》的图书！");
        }
    }

/**
 * 借阅图书的方法
 * @param id 图书的编号
 */
    public void borrowBook(int id){
        // 根据图书编号查找图书
        Book b = findById(id);
        // 如果图书不存在，提示信息并返回
        if(b==null){
            System.out.println("编号"+id+"的图书不存在！");
            return;
        }
        // 检查图书是否已被借出
        if(b.isBorrowed()){
            System.out.println("《"+b.getBookName()+"》已被借出，无法重复借阅。");
            return;
        }
        // 设置图书为已借出状态
        b.setBorrowed(true);
        // 输出借阅成功信息
        System.out.println("借阅成功：《"+b.getBookName()+"》");
        // 保存借阅信息到文件
        saveToFile();
    }

/**
 * 归还图书的方法
 * @param id 图书的编号
 */
    public void returnBook(int id){
    // 根据id查找图书
        Book b = findById(id);
    // 判断图书是否存在
        if(b==null){
            System.out.println("编号"+id+"的图书不存在！");
            return;
        }
    // 检查图书是否已被借出
        if(!b.isBorrowed()){
            System.out.println("《"+b.getBookName()+"》当前是「可借阅」状态，无需归还");
            return;
        }
    // 设置图书为未借出状态
        b.setBorrowed(false);
    // 打印归还成功信息
        System.out.println("归还成功：《"+b.getBookName()+"》");
    // 保存数据到文件
        saveToFile();
    }

/**
 * 使用冒泡排序算法按价格对图书列表进行排序
 * @param asc 排序顺序，true表示升序，false表示降序
 */
    public void sortByPriceBubble(boolean asc){
        int n = booklist.size(); // 获取图书列表的大小
        for(int i=0;i<n-1;i++){  // 外层循环，控制排序轮数
            for(int j=0;j<n-i-1;j++){ // 内层循环，控制每轮比较次数
                Book left = booklist.get(j); // 获取左侧图书
                Book right = booklist.get(j+1); // 获取右侧图书
            // 判断是否需要交换位置
                boolean needSwap = asc?left.getPrice()>right.getPrice():left.getPrice()<right.getPrice();
                if(needSwap){ // 如果需要交换
                    booklist.set(j,right); // 交换图书位置
                    booklist.set(j+1,left);
                }
            }
        }
        saveToFile(); // 将排序后的结果保存到文件
        System.out.println("已按价格"+(asc?"升序":"降序")+"排序"); // 输出排序完成的提示信息
    }

/**
 * 根据书籍价格对书籍列表进行排序
 * @param asc 排序顺序，true表示升序，false表示降序
 */
    public void sortByPrice(boolean asc){
    // 创建一个比较器，用于比较书籍的价格
        Comparator<Book> c = Comparator.comparingDouble(Book::getPrice);
    // 如果不是升序排序，则比较器反转，实现降序排序
        if(!asc){
            c = c.reversed();
        }
    // 使用创建的比较器对书籍列表进行排序
        booklist.sort(c);
    // 将排序后的书籍列表保存到文件中
        saveToFile();
    // 输出排序完成的提示信息，显示是升序还是降序排序
        System.out.println("已按价格"+(asc?"升序":"降序")+"排序"); // 输出排序完成的提示信息
    }

/**
 * 按照书名对图书列表进行排序的方法
 * 使用Java 8的Comparator和lambda表达式进行排序
 * 排序后自动保存到文件，并提示用户排序完成
 */
    public void sortByBookName(){
        // 使用Comparator.comparing方法，通过Book类的getBookName方法获取书名属性进行排序
        booklist.sort(Comparator.comparing(Book::getBookName));
        // 排序完成后调用saveToFile方法将更新后的列表保存到文件
        saveToFile();
        // 向用户输出排序完成的提示信息
        System.out.println("已按书名排序");
    }

/**
 * 显示所有图书信息的方法
 * 当图书列表为空时，提示用户先添加图书
 * 当图书列表不为空时，遍历并打印每本图书的详细信息
 */
    public void showAllBooks(){
        // 检查图书列表是否为空
        if(booklist.isEmpty()){
            // 如果为空，提示用户先添加图书
            System.out.println("暂无图书，请先添加！");
            return;  // 直接返回，不执行后续代码
        }
        // 打印图书列表标题，包含图书总数
        System.out.println("=====图书列表（共"+booklist.size()+"本）=====");
        // 遍历图书列表
        for(Book b:booklist){
            // 打印每本图书的信息
            System.out.println(b);
            // 打印分隔线，使输出更清晰
            System.out.println("------------------------");
        }
    }
}
