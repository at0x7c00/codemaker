package PACKAGE_VALUE;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.lenovots.demo.common.entity.ActionForm;
import com.lenovots.demo.func.entity.enumtype.Sex;
/**
 * @author created by project creator
 * CREATE_DATE
 */
@Entity
@Table(name="TABLE_NAME")
public class BEAN_CLASSNAME extends ActionForm{

    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 登录名
     */
    private String loginName;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 性别
     */
    private Sex sex;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 是否已婚
     */
    private Integer maritalStatus;
    
    /**
     * 固定电话
     */
    private String telephone;
    
    /**
     * 手机
     */
    private String cellphone;
    
    /**
     * 住址
     */
    @NotNull
    @NotEmpty(message="住址不能为空")
    private String address;
    
    /**
     * 角色
     */
    private Role role;
    
    /**
     * 创建日期
     */
    private Date createDate;
    
    /**
     * 备注
     */
    @NotNull
    @NotEmpty(message="备注不能为空")
    private String remark;
    
    private Date createTime;
    private Date birthday;
    
    
    @Id
    @GeneratedValue
    @GenericGenerator(strategy="increment")
    @Column(columnDefinition="integer")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="user_name",nullable=false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name="login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name="marital_status")
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(targetEntity=Role.class)
    @JoinColumn(name="role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Column(name="create_date",updatable=false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="enum")
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
