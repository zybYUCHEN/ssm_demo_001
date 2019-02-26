package com.itcast.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @Auther : 32725
 * @Date: 2019/2/26 12:48
 * @Description: 使用lucene完成简单io全文检索
 * 步骤：
 * 1、创建一个Director对象，指定索引库保存的位置。
 * 2、基于Directory对象创建一个IndexWriter对象
 * 3、读取磁盘上的文件，对应每个文件创建一个文档对象。
 * 4、向文档对象中添加域
 * 5、把文档对象写入索引库
 * 6、关闭indexwriter对象
 * Directory有三个子类
 * FSDirectory，RAMDirectory，FilterDirectory都是接口
 */
public class LuceneDemo1 {

    @Test
    public void test1() throws IOException {

        //1.通过工厂模式创建磁盘文件对象：指定索引库保存位置
        Directory directory = FSDirectory.open(new File("F:\\luceneTest\\index").toPath());
        //2.创建索引输入流对象的配置对象
        IndexWriterConfig writerConfig = new IndexWriterConfig();
        //3.创建索引输入流对象
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
        //4.读取磁盘上的文件，为每一个文件创建对应的文档对象
        File dir = new File("F:\\luceneTest\\searchsource");
        //dir.listFiles()：返回一个抽象路径名数组，
        // 这些路径名表示表示的目录中的文件。
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file);
        }
        //5.遍历数组，设置域对象，以及填充文档对象
        for (File file : files) {
            //获取文件名
            String fileName = file.getName();
            System.out.println(fileName);
            //获取文件路径
            String filePath = file.getPath();
            System.out.println(filePath);
            //获取文件的内容
            String fileContent = FileUtils.readFileToString(file, "utf-8");
            System.out.println(fileContent);
            //获取文件的大小
            long fileSize = FileUtils.sizeOf(file);
            System.out.println(fileSize);
            //创建文本域对象,并存储Field.Store.YES
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);

            //创建文档对象,并添加域对象
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);

            //把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //6.释放资源，关闭流
        indexWriter.close();
    }

    @Test
    public void test2() throws IOException {
        //1.获取磁盘文件对象，并指定索引库的位置
        Directory directory = FSDirectory.open(new File("F:\\luceneTest\\index").toPath());
        //2.获取索引输出流对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //3.创建索引检索器对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建Query查询对象
        Query query = new TermQuery(new Term("content", "spring"));
        //5.执行查询操作,参数一：查询操作具体执行对象，参数二：查询返回结果的最大数
        //  TopDocs：指针容器
        TopDocs topDocs = indexSearcher.search(query, 5);
        //6.获取查询结果的总记录数
        System.out.println(topDocs.totalHits);
        //7.获取获得到的文档
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //8.打印文档中的内容
        for (ScoreDoc doc : scoreDocs) {
            //获取文档id
            int doc1d = doc.doc;
            //根据id取文档对象
            Document document = indexSearcher.doc(doc1d);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println("-----------------寂寞的分割线");
        }
        indexReader.close();
    }

    @Test
    public void test3() throws IOException {

        //1.通过工厂模式创建磁盘文件对象：指定索引库保存位置
        Directory directory = FSDirectory.open(new File("F:\\luceneTest\\index").toPath());
        //2.创建索引输入流对象的配置对象
        IndexWriterConfig writerConfig = new IndexWriterConfig();
        //3.创建索引输入流对象
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
        //4.读取磁盘上的文件，为每一个文件创建对应的文档对象
        File dir = new File("F:\\luceneTest\\searchsource");
        //dir.listFiles()：返回一个抽象路径名数组，
        // 这些路径名表示表示的目录中的文件。
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file);
        }
        //5.遍历数组，设置域对象，以及填充文档对象
        for (File file : files) {
            //获取文件名
            String fileName = file.getName();
            System.out.println(fileName);
            //获取文件路径
            String filePath = file.getPath();
            System.out.println(filePath);
            //获取文件的内容
            String fileContent = FileUtils.readFileToString(file, "utf-8");
            System.out.println(fileContent);
            //获取文件的大小
            long fileSize = FileUtils.sizeOf(file);
            System.out.println(fileSize);
            //创建文本域对象,并存储Field.Store.YES
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);

            //创建文档对象,并添加域对象
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);

            //把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //6.释放资源，关闭流
        indexWriter.close();
    }

    @Test
    public void test4() throws IOException {
        //1.创建一个中文分析器对象
        Analyzer analyzer = new IKAnalyzer();
        //2.使用分析器对象获取一个标记流对象
        TokenStream tokenStream = analyzer.tokenStream("", "2017年12月14日 - " +
                "传智播客Lucene概述公安局Lucene是一款高性能的、可扩展的信息检索(IR)工具库。信息检索是指文档搜索、文档内信息搜索或者文档相关的元数据搜索等操作。");
        //3.向标记流对象中设置一个引用，相当于一个指针
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //4.这里的指针是顺序紊乱的，需要使用方法重置
        tokenStream.reset();
        //5.循环遍历，标记流对象
        while (tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }
        //6.关闭流对象
        tokenStream.close();
    }
}
