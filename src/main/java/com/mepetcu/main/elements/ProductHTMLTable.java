package com.mepetcu.main.elements;

import java.util.List;


public class ProductHTMLTable {

    private final String style;
    private final String header;
    private final StringBuilder rows;
    private final String prevPage;
    private final String nextPage;
    private final StringBuilder reqParams;

    public ProductHTMLTable() {
        this.header = "<div><table>" +
                "<tr>" +
                "<th style=\"width:10%\">ID</th>" +
                "<th style=\"width:40%\">Name</th>" +
                "<th style=\"width:40%\">Category</th>" +
                "<th style=\"width:10%\">Price</th>" +
                "</tr>";
        this.style = "<style>"
                + "table {"
                + "  font-family: arial, sans-serif;"
                + "  border-collapse: collapse;"
                + "  width: 100%;"
                + "}"
                + "td, th {"
                + "  border: 1px solid #dddddd;"
                + "  text-align: left;"
                + "  padding: 8px;"
                + "}"
                + "tr:nth-child(even) {"
                + "  background-color: rgb(180, 180, 255);"
                + "}"
                +".header-button-style {"
                + "    width: 100px;"
                + "    height: 30px;"
                + "    color: rgb(255,255,255);"
                + "    font-size: 1em;"
                + "    text-decoration: none;"
                + "    border-radius: 5px;"
                + "    margin-top: 5px;"
                + "    margin-bottom: 5px;"
                + "    background-color: rgba(95, 39, 158, 1);"
                + "}"
                + ".header-button-style:hover {"
                + "    background-color: rgb(135, 96, 180);"
                + "}"
                + "</style>";
        rows = new StringBuilder();
        reqParams = new StringBuilder();
        prevPage = "<div style=\"display: flex; justify-content: center; width: 50%%;text-align: center\"><a class=\"header-button-style\" style=\"display:flex; align-items: center;justify-content: center\" href =\"/products/page=%d%s\">Previous Page</a></div>";
        nextPage = "<div style=\"display: flex; justify-content: center; width: 50%%;text-align: center\"><a class=\"header-button-style\" style=\"display:flex; align-items: center;justify-content: center\" href =\"/products/page=%d%s\">Next Page</a></div>";
    }

    public void addProducts(List<Product> products) {
        for (Product p : products)
            rows.append(String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%.2f</td></tr>", p.getId(), p.getName(), p.getCategory(), p.getPrice()));
    }

    public void setReqParams(String filter,String filterBy,String sort,String sortBy){
        reqParams.append("?filter=").append(filter);
        reqParams.append("&filterBy=").append(filterBy);
        reqParams.append("&sort=").append(sort);
        reqParams.append("&sortBy=").append(sortBy);
    }

    public String build(int page, int pages, boolean hasNext) {
        StringBuilder hrefs = new StringBuilder();
        hrefs.append(String.format("<div style=\"text-align: center\"><a>Page %d/%d<a></div>", page, pages));
        hrefs.append("<div style=\"display: flex; justify-content: center\">");
        if (page > 1) hrefs.append(String.format(prevPage, page - 1, reqParams));
        else hrefs.append("<div style=\"width: 50%\"></div>");
        if (hasNext) hrefs.append(String.format(nextPage, page + 1, reqParams));
        else hrefs.append("<div style=\"width: 50%\"></div>");
        hrefs.append("</div>");
        return style + hrefs + header + rows + "</table></div>";
    }

    public void clear() {
        rows.setLength(0);
        reqParams.setLength(0);
    }

}
