package com.mycontacts.common;

// organization contact with industry and website
public class Organization extends Contact {
    private String industry;
    private String website;

    public Organization(String name) {
        super(name);
    }

    public String getIndustry() { return industry; }
    public String getWebsite() { return website; }

    public void setIndustry(String industry) { this.industry = industry; }
    public void setWebsite(String website) { this.website = website; }

    @Override
    public String getContactType() {
        return "Organization";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (industry != null && !industry.isEmpty()) {
            sb.append("  Industry: ").append(industry).append("\n");
        }
        if (website != null && !website.isEmpty()) {
            sb.append("  Website: ").append(website).append("\n");
        }
        return sb.toString();
    }
}
