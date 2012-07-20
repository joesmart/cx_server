package com.server.cx.entity.cx;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.server.cx.xml.adapter.DateXMLAdapter;
import com.server.cx.xml.adapter.SignatureXMLAdapter;

@XmlRootElement
@XmlType(propOrder={"signature","name","type","category","level","deadline","validTime","uploadTime","isServer","price","path","fileType","fileName","fileData","thumbnailPath"})
@Entity
@Table(name="cxinfo")
public class CXInfo extends AuditableEntity{
	private Signature signature;
	private String name;
	private String type;
	private String category;
	private int level;
	private long deadline;
	private Date validTime;
	private Date uploadTime;
	private int isServer;
	private Double price;
	private String path;
	private String fileName;
	private String fileType;
	private String thumbnailPath;
	
	private String fileData;

	public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    @Transient
    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(nullable=true)
    public long getDeadline() {
		return deadline;
	}
    @Column(nullable=true,columnDefinition="Integer default '-1'")
	public int getIsServer() {
		return isServer;
	}

    @Column(nullable=true,columnDefinition="Integer default '-1'")
	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	
	@Transient
	public Long getCx_id() {
		return getId();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public void setIsServer(int isServer) {
		this.isServer = isServer;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @XmlJavaTypeAdapter(DateXMLAdapter.class)
	@Column(nullable=true)
	public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }
    @XmlJavaTypeAdapter(DateXMLAdapter.class)
    
    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @XmlJavaTypeAdapter(SignatureXMLAdapter.class)
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "signature_id")
	public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    
    
}
