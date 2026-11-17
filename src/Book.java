public abstract class Book {
    private int id;
    private String bookName;
    private String author;
    private String publisher;
    private double price;
    private boolean isBorrowed;

    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public abstract String getType();

    public abstract String getExtraInfo();

    public abstract String getExtraData();

    public Book(int id,String bookName,String author,String publisher,double price,boolean isBorrowed){
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.isBorrowed = isBorrowed;
    }
    @Override
    public String toString(){
        return "图书编号："+id+"\n图书名称："
                +bookName+"\n作者："
                +author+"\n出版社："
                +publisher+"\n价格："
                +price+"\n"+"类型："
                +getType()+"\n"
                +"状态："+(isBorrowed?"已借出":"可借阅")+"\n"
                +getExtraInfo();
    }
}
