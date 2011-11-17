/**
 * Date: 14.11.2011
 * Time: 5:28:29
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.core.model;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:28:29
 */
public class Query {
    private long id;
    private long queryUniqueId;
    private String text;
    private long time;

    public Query(long id, long queryUniqueId, String text, long date) {
        this.id = id;
        this.queryUniqueId = queryUniqueId;
        this.text = text;
        this.time = date;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
