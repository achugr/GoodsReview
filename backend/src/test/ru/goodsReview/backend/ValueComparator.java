/*
* Date: 25.11.11
* Time: 03:17
* Author: 
* Artemij Chugreev 
* artemij.chugreev@gmail.com
*/
package ru.goodsReview.backend;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ruslan
 * Date: 25.11.11
 * Time: 3:17
 * To change this template use File | Settings | File Templates.
 */
public class ValueComparator implements Comparator{
       public int compare(Object object1, Object object2){
           Map.Entry me1 = (Map.Entry) object1;
           Map.Entry me2 = (Map.Entry) object2;
           Comparable comparable1 = (Comparable) me1.getValue();
           Comparable comparable2 = (Comparable) me2.getValue();
           return comparable2.compareTo(comparable1);
       }
}
