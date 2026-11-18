public class EBook extends Book {
    private double fileSizeMB;
    private String downLoadUrl;

    public EBook() {
        super();
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }

    public EBook(int id,String bookName,String author,String publisher,double price,boolean isBorrowed,double fileSizeMB,String downLoadUrl){
        super(id,bookName,author,publisher,price,isBorrowed);
        this.fileSizeMB = fileSizeMB;
        this.downLoadUrl = downLoadUrl;
    }

    @Override
    public String getType(){
        return "EBOOK";
    }

    @Override
    public String getExtraInfo(){
        return "文件大小："+fileSizeMB+"MB\n"+"下载地址："+downLoadUrl;
    }

    @Override
    public String getExtraData(){
        return fileSizeMB+"|"+downLoadUrl;
    }
}
