package com.attribe.waiterapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemImage implements Serializable{
	
	 private int id;
	 private String name;
	 private int category_menu_id;
	 private String created_at;
	 private String updated_at;
	 private int category_id;
	 private List<Image> images = new ArrayList<Image>();
	 private String item_imageUrl;
	 
	 public ItemImage(int id,String name ,int category_id, int category_menu_id,String created_at,String updated_at, ArrayList<Image> itemImages){
		 
		 this.id = id;
		 this.name = name;
		 this.category_id = category_id;
	     this.category_menu_id = category_menu_id;
	     this.created_at = created_at;
	     this.updated_at = updated_at;
	     this.images = itemImages;
		 
	 }
	 
 public ItemImage(int id,String name ,int category_id, int category_menu_id,String created_at,String updated_at,String imageUrl){
		 
		 this.id = id;
		 this.name = name;
		 this.category_id = category_id;
	     this.category_menu_id = category_menu_id;
	     this.created_at = created_at;
	     this.updated_at = updated_at;
	     this.item_imageUrl = imageUrl;
		 
	 }
 
 
 public ItemImage(int id,String name ,int category_id, int category_menu_id,String created_at,String updated_at){
	 
	 this.id = id;
	 this.name = name;
	 this.category_id = category_id;
     this.category_menu_id = category_menu_id;
     this.created_at = created_at;
     this.updated_at = updated_at;
    
	 
 }
	 
	 public ItemImage(int category_menu_id){
		 
		 this.category_menu_id = category_menu_id;
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
	 
	 public int getCategory_id() {
			return category_id;
		}

		public void setCategory_id(int category_id) {
			this.category_id = category_id;
		}
	 
	 public int getCategory_Menu_id() {
			return category_menu_id;
		}

     public void setCategory_Menu_id(int category_menu_id) {
			this.category_menu_id = category_menu_id;
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

	 
	 public List<Image> getItemImages() {
	        return images;
	    }

	    public void setItemImages(List<Image> images) {
	        this.images = images;
	    }
	    
}
