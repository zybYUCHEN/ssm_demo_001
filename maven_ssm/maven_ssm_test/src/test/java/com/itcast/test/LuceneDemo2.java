package com.itcast.test;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
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
 * @Date: 2019/2/26 19:10
 * @Description:
 */
public class LuceneDemo2 {

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

    @Test
    public void test4(){

    }

}
