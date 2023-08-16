import java.util.ArrayList;

public class Pet {
    public long id;
    public Category category;
    public String name;
    public ArrayList<String> photoUrls;
    public ArrayList<Tag> tags;
    public String status;

    public Pet() {
    }

    public Pet(long id, Category category, String name, ArrayList<String> photoUrls, ArrayList<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }
}

