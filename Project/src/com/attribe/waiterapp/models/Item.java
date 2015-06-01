package com.attribe.waiterapp.models;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabih Ahmed on 5/11/2015.
 */
public class Item implements Serializable {

    private int id;
    private String name;
    private String description;
    private String created_at;
    private String updated_at;
    private double price;
    private int category_id;
    private List<Image> images = new ArrayList<Image>();
    private boolean selected;
    private byte[] imageBlob;

    /**
     *
     * @param name
     * @param description
     * @param price
     */
    public Item(int id, String name, String description, double price, int category_id, byte[] imagesBlob){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.imageBlob = imagesBlob;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

    public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	/**
     *
     * @return price
     */

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImagesBlobList(byte[] imagesBlob) {
        this.imageBlob = imagesBlob;
    }

    public class Image {

        private Integer id;
        private String url;
        private String created_at;
        private String updated_at;

        public Integer getId() {
            return id;
        }

        public String getCreated_at() {
			return created_at;
		}



		public void setCreated_at(String created_at) {
			this.created_at = created_at;
		}



		public String getUpdated_at() {
			return updated_at;
		}



		public void setUpdated_at(String updated_at) {
			this.updated_at = updated_at;
		}



		public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }


        public void setUrl(String url) {
            this.url = url;
        }
    }
}

