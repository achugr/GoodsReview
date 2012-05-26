package ru.goodsreview.analyzer;

/*
 *  Date: 15.10.11
 *   Time: 0:32
 *   Author:
 *      Artemij Chugreev
 *      artemij.chugreev@gmail.com
 */


public class Test {
   /* private static SimpleJdbcTemplate jdbcTemplate;

    private static final Logger log = Logger.getLogger(Test.class);

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map.Entry<Long, Double>> SortMap(Map<Long, Double> map){
        List<Map.Entry<Long, Double>> list = new ArrayList(map.entrySet());
        Collections.sort(list, new ValueComparator());
        return list;
    }

    //TODO Ruslan, please fix this code
    public static void  testTfIdf() throws StorageException {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "storage/src/scripts/beans.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);


        ReviewDbController reviewDbController = new ReviewDbController(simpleJdbcTemplate);
        ProductDbController productDbController = new ProductDbController();
        ThesisDbController th = new ThesisDbController(simpleJdbcTemplate);

        AnalyzeThesis analyzeThesis = new AnalyzeThesis(simpleJdbcTemplate);
        int size =  productDbController.getAllProducts().size();
        //or(int i = 1; i < size; i++){
             //analyzeThesis.updateThesisByProductId(i);
        //}
        Map<Long, Double> tfidf = analyzeThesis.mapOfTFIDF(reviewDbController.getAllReviews());
        analyzeThesis.printTFIDF(tfidf);
    }
    public static void main(String [] args) throws StorageException {
        testTfIdf();
    }
     */
}
