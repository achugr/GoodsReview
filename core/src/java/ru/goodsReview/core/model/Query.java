/**
 * Date: 14.11.2011
 * Time: 5:28:29
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.core.model;

import java.util.Date;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:28:29
 */
public class Query {
    private long id;
    private long queryUniqueId;
    private String text;
    private Date date;

    public Query(long id, long queryUniqueId, String text, Date date) {
        this.id = id;
        this.queryUniqueId = queryUniqueId;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQueryUniqueId() {
        return queryUniqueId;
    }

    public void setQueryUniqueId(long queryUniqueId) {
        this.queryUniqueId = queryUniqueId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
