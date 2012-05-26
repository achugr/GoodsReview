/**
 * Date: 16.11.2011
 * Time: 1:24:09
 * Author:
 *   Sergey Serebryakov
 *   sergey.serebryakoff@gmail.com
 */

package ru.goodsreview.core.db.exception;

/**
 * User: Sergey Serebryakov
 * Date: 16.11.2011
 * Time: 1:24:09
 */
public class StorageException extends Exception {
    String message; // isn't really needed

    public StorageException() {
        super();
        this.message = "Unknown";
    }

    public StorageException(String message) {
        super(message);
        this.message = message;
    }
}
