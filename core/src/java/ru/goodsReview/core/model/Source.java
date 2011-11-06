/**
 * Date: 30.10.2011
 * Time: 23:31:37
 * Author: 
 *   Sergey Serebryakov 
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsReview.core.model;

/**
 * User: Sergey Serebryakov
 * Date: 30.10.2011
 * Time: 23:31:37
 */
public class Source {
    private long id;
    private String name;
    private String mainPageUrl;

    public Source(long id, String name, String mainPageUrl) {
        this.id = id;
        this.name = name;
        this.mainPageUrl = mainPageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainPageUrl() {
        return mainPageUrl;
    }

    public void setMainPageUrl(String mainPageUrl) {
        this.mainPageUrl = mainPageUrl;
    }
}
