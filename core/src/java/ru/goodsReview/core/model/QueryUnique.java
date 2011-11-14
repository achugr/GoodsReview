/**
 * Date: 14.11.2011
 * Time: 5:33:17
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.core.model;

import java.util.Date;

/**
 * User: Sergey Serebryakov
 * Date: 14.11.2011
 * Time: 5:33:17
 */
public class QueryUnique {
    private long id;
    private String text;
    private Date lastScan;
    private int frequency;

    public QueryUnique(long id, String text, Date last_scan, int frequency) {
        this.id = id;
        this.text = text;
        this.lastScan = last_scan;
        this.frequency = frequency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastScan() {
        return lastScan;
    }

    public void setLastScan(Date lastScan) {
        this.lastScan = lastScan;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
