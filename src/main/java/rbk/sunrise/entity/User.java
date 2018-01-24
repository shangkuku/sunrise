package rbk.sunrise.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;
import rbk.sunrise.base.BaseEntity;
import rbk.sunrise.base.IdOnlyEntity;

@Table(name = "cloud_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends IdOnlyEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    @NotNull
    private String name;

    /**
     * 所属学院
     */
    @Column(name = "college_id")
    private Long collegeId;

    /**
     * 所属专业
     */
    @Column(name = "profession_id")
    private Long professionId;

    /**
     * 所属班级
     */
    @Column(name = "class_id")
    private Long classId;

    /**
     * 邮箱地址
     */
    private String mail;

    /**
     * 手机号码
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 工号
     */
    @Column(name = "job_number")
    private String jobNumber;

    /**
     * 职称
     */
    @Column(name = "job_title")
    private String jobTitle;

    /**
     * 学号
     */
    @Column(name = "student_number")
    private Integer studentNumber;

    @Column(name = "role_id")
    private Long roleId;

    private String salt;
}