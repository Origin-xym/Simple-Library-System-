import java.util.Scanner;
public class Main {
/**
 * 主方法，程序的入口点
 * @param args 命令行参数
 */
    public static void main(String[] args){
    // 创建Scanner对象用于接收用户输入
        Scanner sc = new Scanner(System.in);
    // 创建图书管理器对象
        BookManager manager = new BookManager();
    // 无限循环，持续显示菜单直到用户选择退出
        while (true){
        // 打印功能菜单
            printMenu();
        // 读取用户输入的功能编号
            int choice = readInt(sc,"请输入功能编号：");
        // 根据用户选择执行相应功能
            switch (choice){
                case 1:
                    addBookUI(sc,manager);  // 添加图书界面
                    break;
                case 2:
                    manager.showAllBooks();  // 显示所有图书
                    break;
                case 3:
                    searchUI(sc,manager);  // 按书名查询界面
                    break;
                case 4:
                    borrowUI(sc,manager);  // 借阅图书界面
                    break;
                case 5:
                    returnUI(sc,manager);  // 归还图书界面
                    break;
                case 6:
                    deleteUI(sc,manager);  // 删除图书界面
                    break;
                case 7:
                    updateUI(sc,manager);  // 修改图书界面
                    break;
                case 8:
                    sortUI(sc,manager);  // 图书排序界面
                    break;
                case 0:
                // 输出退出信息
                    System.out.println("感谢使用，再见！");
                    sc.close();  // 关闭Scanner对象
                    return;  // 退出程序
                default:
                    System.out.println("输入错误，请重新选择！");
            }
        }
    }

/**
 * 从控制台读取一个整数输入的方法
 * @param sc Scanner对象，用于读取控制台输入
 * @param tip 提示信息，用于引导用户输入
 * @return 用户输入的整数
 */
    private static int readInt(Scanner sc,String tip){
    // 使用无限循环确保用户必须输入一个有效的整数
        while(true){
        // 打印提示信息
            System.out.print(tip);
        // 读取一行输入并去除前后空格
            String line = sc.nextLine().trim();
            try{
            // 尝试将输入转换为整数并返回
                return Integer.parseInt(line);
            }catch (NumberFormatException e){
            // 如果转换失败，提示用户重新输入
                System.out.println("请输入整数数字");
            }
        }
    }

/**
 * 从控制台读取一个大于0的双精度浮点数
 * @param sc Scanner对象，用于读取控制台输入
 * @param tip 提示信息，用于引导用户输入
 * @return 用户输入的有效双精度浮点数（大于0）
 */
    private static double readDouble(Scanner sc,String tip){
        while(true){  // 无限循环，直到获取到有效输入
            System.out.print(tip);  // 打印提示信息
            String line = sc.nextLine().trim();  // 读取一行输入并去除首尾空格
            try{
                double v = Double.parseDouble(line);  // 尝试将输入字符串转换为double类型
                if(v<=0){  // 检查输入值是否大于0
                    System.out.println("价格必须大于0，请重新输入！");  // 提示用户输入必须大于0
                    continue;  // 继续下一次循环，要求重新输入
                }
                return v;  // 返回有效的输入值
            }catch (NumberFormatException e){  // 处理输入不是有效数字的情况
                System.out.println("请输入有效数字！");  // 提示用户输入有效数字
            }
        }
    }

/**
 * 从控制台读取用户输入的字符串
 * @param sc Scanner对象，用于接收用户输入
 * @param tip 提示信息，用于引导用户输入
 * @return 用户输入的非空字符串
 */
    private static String readString(Scanner sc,String tip){
        while (true){  // 无限循环，直到用户输入有效内容
            System.out.print(tip);  // 打印提示信息
            String s = sc.nextLine().trim();  // 读取用户输入并去除首尾空格
            if(!s.isEmpty()){  // 检查输入是否为空
                return s;  // 返回用户输入
            }
            System.out.println("内容不能为空，请重新输入！");  // 提示用户输入不能为空
        }
    }

/**
 * 添加图书的界面方法
 * @param sc Scanner对象，用于接收用户输入
 * @param manager BookManager对象，用于管理图书
 */
    private static void addBookUI(Scanner sc,BookManager manager){
        System.out.println("===添加图书 ===");
    // 读取用户输入的图书编号
        int id = readInt(sc,"请输入图书编号：");
    // 读取用户输入的图书名称
        String bookName = readString(sc,"请输入图书名称：");
    // 读取用户输入的作者
        String author = readString(sc,"请输入作者：");
    // 读取用户输入的出版社
        String publisher = readString(sc,"请输入出版社：");
    // 读取用户输入的图书价格
        double pricce = readDouble(sc,"请输入价格：");
    // 读取用户输入的图书类型
        int type = readInt(sc,"请输入图书类型（1.纸质书 2.电子书）：");
        Book book;
    // 根据用户选择的类型创建不同类型的图书对象
        if(type==1){
        // 如果是纸质书，则读取页数和馆藏位置信息
            int pageCount = readInt(sc,"请输入页数：");
            String location = readString(sc,"请输入馆藏位置：");
        // 创建纸质书对象
            book = new PaperBook(id,bookName,author,publisher,pricce,false,pageCount,location);
        } else if (type==2) {
        // 如果是电子书，则读取文件大小和下载地址信息
            double fileSize = readDouble(sc,"请输入文件大小（MB）：");
            String url = readString(sc,"请输入下载地址：");
        // 创建电子书对象
            book = new EBook(id,bookName,author,publisher,pricce,false,fileSize,url);
        }else {
        // 如果类型选择有误，提示用户并取消添加
            System.out.println("类型选择有误，添加已取消！");
            return;
        }
    // 将创建的图书对象添加到图书管理器中
        manager.addBook(book);
    }

/**
 * 删除图书的界面方法
 * @param sc Scanner对象，用于接收用户输入
 * @param manager BookManager对象，用于管理图书数据
 */
    private static void deleteUI(Scanner sc,BookManager manager){
    // 调用readInt方法读取用户输入的图书编号
        int id = readInt(sc,"请输入要删除的图书编号：");
    // 调用BookManager的deleteBook方法删除指定编号的图书
        manager.deleteBook(id);
    }

/**
 * 更新图书信息的UI界面方法
 * @param sc Scanner对象，用于接收用户输入
 * @param manager BookManager对象，用于管理图书数据
 */
    private static void updateUI(Scanner sc,BookManager manager){
    // 读取用户输入的图书编号
        int id = readInt(sc,"请输入要修改的图书编号：");
    // 检查该编号的图书是否存在
        if(manager.findById(id)==null){
            System.out.println("编号"+id+"的图书不存在！");
            return;
        }


    // 读取用户输入的新图书信息
        String bookName = readString(sc,"请输入新的图书名称：");  // 新图书名称
        String author = readString(sc,"请输入新的图书作者：");    // 新图书作者
        String publisher = readString(sc,"请输入新的图书出版社："); // 新图书出版社
        double price = readDouble(sc,"请输入新的图书价格：");     // 新图书价格
    // 尝试更新图书信息并输出结果
        if(manager.updateBook(id,bookName,author,publisher,price)){
            System.out.println("修改成功！");
        }else {
            System.out.println("修改失败:图书不存在！");
        }
    }

/**
 * 搜索图书界面的方法
 * @param sc Scanner对象，用于接收用户输入
 * @param manager BookManager对象，用于管理图书操作
 */
    private static void searchUI(Scanner sc,BookManager manager){
    // 读取用户输入的搜索关键词
        String keyword = readString(sc,"请输入要搜索的关键词：");
    // 调用BookManager的findBookName方法进行图书搜索
        manager.findBookName(keyword);
    }

/**
 * 借阅图书的用户界面方法
 * @param sc Scanner对象，用于获取用户输入
 * @param manager BookManager对象，用于管理图书操作
 */
    private static void borrowUI(Scanner sc,BookManager manager){
    // 调用readInt方法读取用户输入的图书编号
        int id = readInt(sc,"请输入要借阅的图书编号：");
    // 调用BookManager的borrowBook方法执行借书操作
        manager.borrowBook(id);
    }

/**
 * 归还图书的方法
 * @param sc Scanner对象，用于接收用户输入
 * @param manager 图书管理器对象，用于处理图书归还操作
 */
    private static void returnUI(Scanner sc,BookManager manager){
    // 读取用户输入的图书编号
        int id = readInt(sc,"请输入要归还的图书编号：");
    // 调用图书管理器的归还图书方法处理归还操作
        manager.returnBook(id);
    }

/**
 * 图书排序界面方法
 * 提供图书排序的交互界面，用户可以选择不同的排序方式
 * @param sc Scanner对象，用于获取用户输入
 * @param manager BookManager对象，用于管理图书并进行排序操作
 */
    private static void sortUI(Scanner sc,BookManager manager){
        System.out.println("===图书排序 ===");  // 打印排序菜单标题
        System.out.println("1.按价格升序");     // 打印价格升序选项
        System.out.println("2.按价格降序");     // 打印价格降序选项
        System.out.println("3.按书名升序");     // 打印书名升序选项
        int choice = readInt(sc,"请输入排序方式：");  // 调用readInt方法获取用户输入的排序方式
        switch(choice){  // 根据用户输入进行不同的排序操作
            case 1:  // 用户选择1，按价格升序排序
                manager.sortByPrice(true);
                break;
            case 2:  // 用户选择2，按价格降序排序
                manager.sortByPrice(false);
                break;
            case 3:  // 用户选择3，按书名升序排序
                manager.sortByBookName();
                break;
            default:  // 用户输入无效选项
                System.out.println("选择有误，已返回主菜单。");
                break;
        }
        manager.showAllBooks();  // 显示排序后的所有图书
    }

/**
 * 打印图书管理系统的菜单界面
 * 该方法用于向控制台输出系统菜单选项，供用户选择操作
 */
    private static void printMenu(){
        System.out.println();  // 输出一个空行，用于格式化显示
        System.out.println("===图书管理系统===");  // 打印菜单标题
        System.out.println("1.添加图书");  // 打印添加图书选项
        System.out.println("2.显示所有图书");  // 打印显示所有图书选项
        System.out.println("3.按书名查询");  // 打印按书名查询选项
        System.out.println("4.借阅图书");  // 打印借阅图书选项
        System.out.println("5.归还图书");  // 打印归还图书选项
        System.out.println("6.删除图书");  // 打印删除图书选项
        System.out.println("7.修改图书信息");  // 打印修改图书信息选项
        System.out.println("8.图书排序");  // 打印图书排序选项
        System.out.println("0.退出系统");  // 打印退出系统选项
    }
}
