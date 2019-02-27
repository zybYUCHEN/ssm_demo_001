package com.itcast.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @Auther : 32725
 * @Date: 2019/2/26 19:10
 * @Description: lucene是全文搜索引擎
 */
public class LuceneTest {

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 创建索引库
     * 使用lucene，分析器为IKAnalyzer，中文解析器
    **/
    @Test
    public void testLucene()throws Exception{
        //1.创建磁盘目录对象，指定索引库保存位置
        Directory directory = FSDirectory.open(new File("F:\\luceneTest\\index").toPath());
        //2.创建索引配置
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        //3.创建索引输入流
        IndexWriter indexWriter = new IndexWriter(directory,config);
        //4.获取文件抽象路径名数组
        File dir = new File("F:\\luceneTest\\searchsource");
        File[] files = dir.listFiles();
        //5.遍历数组
        for (File file : files) {
            //获取文件的各项属性
            //获取文件名
            String fileName = file.getName();
            //获取文件路径
            String filePath = file.getPath();
            //获取文件文本内容
            String fileContent = FileUtils.readFileToString(file, "utf-8");
            //获取文件大小
            long fileSize = FileUtils.sizeOf(file);

            //6.设置域对象，将文件中各项属性分别存入各自的域对象中
            /*
            *  TextField:文本域：参数一：域名 参数二：域中存储的数据 参数三：是否存储
            *            会分析词，会创建索引，Field.Store.YES控制存储
            *  StoredField:存储域：只存储
            **/
            Field name = new TextField("fileName",fileName, Field.Store.YES );
            Field path = new StoredField("filePath", filePath);
            Field content = new TextField("fileContent",fileContent, Field.Store.YES);
            Field size = new LongPoint("fileSize",fileSize);
            Field sizeValue = new StoredField("fileSize", fileSize);

            //获取文档对象
            Document document = new Document();
            document.add(name);
            document.add(path);
            document.add(content);
            document.add(size);
            document.add(sizeValue);
            //把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //关闭流资源
        indexWriter.close();
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 查询索引库中的数据
    **/
    @Test
    public void test2()throws Exception{
        //1.创建directory对象
        Directory directory = FSDirectory.open(new File("F:\\luceneTest\\index").toPath());
        //2.创建一个索引读取流
        IndexReader indexReader = DirectoryReader.open(directory);
        //3.创建一个索引搜索器
        IndexSearcher searcher = new IndexSearcher(indexReader);
        //4.创建查询执行对象
        Query query = new TermQuery(new Term("fileName", "spring"));
        //5.执行查询,获取一个查询结果容器
        TopDocs topDocs = searcher.search(query, 10);
        //6.获取总记录数
        long totalHits = topDocs.totalHits;
        System.out.println(totalHits);
        //7.获取被选上的文档列表，查询结果最大显示10条
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //8.遍历文档列表
        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取文档id
            int id = scoreDoc.doc;
            //根据id获取文档对象
            Document doc = searcher.doc(id);
            //有了文档对象，就可以获取文档的各个域中的数据
            System.out.println(doc.getField("fileName"));
            System.out.println("----------------------------------------");
        }
        //9.关闭流资源
        indexReader.close();
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 测试中文分析器
     * 可以添加词汇到hotword.dic
     * 可以添加无意义或铭感词到stopword.dic
    **/
    @Test
    public void test3() throws IOException {
        //1.获取中文解析器
        Analyzer analyzer = new IKAnalyzer();
        //2.获取标记流
        TokenStream tokenStream = analyzer.tokenStream("", "2017年12月14日 - 传智播客Lucene概述公安局Lucene是一款高性能的、可扩展的信息检索(IR)工具库。信息检索是指文档搜索、文档内信息搜索或者文档相关的元数据搜索等操作。");
        //3.向标记流中添加一个指针，指向流中的某个关键词对象
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //4.获取到的指针对象是乱序的，需要重置
        tokenStream.reset();
        //5.使用while循环遍历标记流,指针会随之而动
        while(tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }
        //6.关闭标记流
        tokenStream.close();
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 根据文档大小进行范围查询
    **/
    @Test
    public void test4()throws Exception{
        //0.创建磁盘目录对象
        Directory directory = FSDirectory.open(new File("F:\\luceneTest\\index").toPath());
        //1.创建查询执行对象
        Query query = LongPoint.newRangeQuery("fileSize", 1, 50);
        //2.创建索引输出流对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //3.创建索引检索器对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.执行查询,获取结果容器
        TopDocs topDocs = indexSearcher.search(query, 10);
        //5.获取被选中的文档数组
        ScoreDoc[] docs = topDocs.scoreDocs;
        //6.遍历文档数组，获取想要的值
        for (ScoreDoc doc : docs) {
            //1.获取文档对象id
            int id = doc.doc;
            //2.根据文档id查找对应的文档对象
            Document document = indexReader.document(id);
            //3.获取想要的数据，这里我取文档名称
            IndexableField fileName = document.getField("fileName");
            System.out.println(fileName);
            IndexableField fileSize = document.getField("fileSize");
            System.out.println(fileSize+"-------------------寂寞的分割线");
        }
        //7.释放资源
        indexReader.close();
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 类似百度搜索，最常用搜索
     * 功能描述： 输入一句话，先把这句话分词，再根据分词进行查询
     * 查询对象分类：
     *  new TermQuery（）：关键词查询
     *  LongPoint.newRangeQuery（）:根据数值进行范围查询
     *  new QueryParser(): 查询解析器,把用户输入的语句，先进行分词，在进行查询
    **/
    @Test
    public void test5() throws Exception {
        //1.创建查询分析器对象,共两个参数： 参数一：查询的域，参数二：分析器
        QueryParser queryParser = new QueryParser("fileName",new IKAnalyzer());
        //2模拟用户查询
        Query query = queryParser.parse("lucene是一个Java开发的全文检索工具包");
        //3.执行查询
        //0.创建磁盘目录对象
        Directory directory = FSDirectory.open(new File("f:\\luceneTest\\index").toPath());
        //1.创建输出流
        IndexReader reader = DirectoryReader.open(directory);
        //2.创建索引检索器
        IndexSearcher searcher = new IndexSearcher(reader);
        //3.执行查询
        TopDocs search = searcher.search(query, 10);
        System.out.println(search.totalHits);
        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc doc : scoreDocs) {
            int id = doc.doc;
            Document document = reader.document(id);
            IndexableField fileName = document.getField("fileName");
            System.out.println(fileName);
            System.out.println(document.getField("fileSize"));
        }
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 索引库的维护
     * 添加新索引操作
    **/
    @Test
    public void test6()throws Exception{
        //添加新索引和创建索引库操作一样
        //0.创建磁盘目录对象,和输入流配置对象
        Directory directory = FSDirectory.open(new File("f:\\luceneTest\\index").toPath());
        IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
        //1.创建输入流
        IndexWriter writer = new IndexWriter(directory,config);
        //2.创建新添加文件所在的目录
        File dir = new File("F:\\luceneTest");
        //3.使用工具类，获取文件抽象路径名
        File[] files = dir.listFiles();
        //4.遍历数组
        for (File file : files) {
            System.out.println(file);
        }
        for (File file : files) {
            //加一个判断，如果file是文件夹，就continue
            if (file.isDirectory()){
                continue;
            }
            //获取文件名
            String fileName = file.getName();
            //获取文件路径
            String filePath = file.getPath();
            //使用工具类，获取文件大小
            long fileSize = FileUtils.sizeOf(file);
            //使用工具类，获取文件内容
            String fileContent = FileUtils.readFileToString(file, "utf-8");

            //添加域对象
            TextField name = new TextField("fileName", fileName, Field.Store.YES);
            TextField content = new TextField("fileContent", fileContent, Field.Store.YES);
            StoredField path = new StoredField("filePath", filePath);
            LongPoint size = new LongPoint("fileSize", fileSize);
            StoredField sizeValue = new StoredField("fileSize", fileSize);

            //创建文档对象
            Document document = new Document();
            document.add(name);
            document.add(content);
            document.add(path);
            document.add(size);
            document.add(sizeValue);
            //写入索引库
          writer.addDocument(document);
        }
        //释放资源
        writer.close();
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 删除操作
     *          有两种：
     *              1.全删（注意安全）
     *              2.根据条件查询，选择性删除（常用）
     *
    **/
    @Test
    public void test7()throws Exception{
        //1.创建索引输入流
        IndexWriter indexWriter = new IndexWriter(
                FSDirectory.open(new File("f:\\lucene\\index").toPath()),
        new IndexWriterConfig(new IKAnalyzer()));
        //2.全部删除，慎重
        indexWriter.deleteAll();
        //3.释放资源
        indexWriter.close();
    }
    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 选择删除
    **/
    @Test
    public void test8()throws Exception{
        //1.创建索引输入流
        IndexWriter indexWriter = new IndexWriter(
                FSDirectory.open(new File("f:\\luceneTest\\index").toPath()),
                new IndexWriterConfig(new IKAnalyzer()));
        //2.删除查询出来的文档对象
        indexWriter.deleteDocuments(new Term("fileName","apache"));
        //3.释放资源
        indexWriter.close();
    }

    /**
    * @Author: 32725
    * @Param: []
    * @Return: void
    * @Description: 更新操作，先删除后添加
    **/
    @Test
    public void test9()throws  Exception{
        //1.创建索引输入流
        IndexWriter indexWriter = new IndexWriter(
                FSDirectory.open(new File("f:\\luceneTest\\index").toPath()),
                new IndexWriterConfig(new IKAnalyzer()));
        //1.创建一个新的文档对象，这里是模拟，真实这个文档对象
        //也是从原始文档读取出来的
        Document document = new Document();
        //向文档对象中添加域
        document.add(new TextField("name", "更新之后的文档", Field.Store.YES));
        document.add(new TextField("name1", "更新之后的文档2", Field.Store.YES));
        document.add(new TextField("name2", "更新之后的文档3", Field.Store.YES));

        //执行更新操作
        indexWriter.updateDocument(new Term("fileName","spring"), document);
        //释放资源
        indexWriter.close();
    }
}
