# 数据库初始化
#
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id bigbigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete tinybigint default 0 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id bigbigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   bigint   default 0                 not null comment '点赞数',
    favourNum  bigint   default 0                 not null comment '收藏数',
    userId bigbigint not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete tinybigint default 0 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id bigbigint auto_increment comment 'id' primary key,
    postId bigbigint not null comment '帖子 id',
    userId bigbigint not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id bigbigint auto_increment comment 'id' primary key,
    postId bigbigint not null comment '帖子 id',
    userId bigbigint not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '医疗记录';


# 医疗记录主表：id，患者ID，医疗状态,创建时间，更新时间，逻辑删除
# 医疗详情表:id,医疗记录id,接诊医生，接诊科室，接诊地点，接诊时间，患者主诉，现病史,创建时间，更新时间，创建人，逻辑删除
# 体征采集情况表 ： id , 医疗记录id, 体温, 脉搏，呼吸，血压，血氧，体重，身高，bmi，辅助检查 创建时间, 更新时间, 创建人, 逻辑删除
# 诊断结果表：id ,医疗记录id ，诊断结果，医嘱，开放时间，随访计划，处方记录，治疗方案，创建时间，更新时间，创建人，逻辑删除
# 化疗记录表： id , 医疗记录id, 化疗时间，方案开具时间，开方人，值班医生，治疗开始时间,治疗结束时间，静脉通路，创建时间，更新时间，创建人，逻辑删除
# 化疗记录体征采集表：id , 化疗记录id, 体温, 脉搏，呼吸，血压，血氧，体重，身高，bmi，检验结果，辅助检查 创建时间, 更新时间, 创建人, 逻辑删除
# 化疗记录当日评估表 ：id , 化疗记录id, 评估时间，评估内容，评估医生，创建时间，更新时间，创建人，逻辑删除
# 化疗记录药物表：id , 化疗记录id, 药物名称，剂量，用法，开方人，创建时间，更新时间，创建人，逻辑删除
# 化疗后记录表：id , 化疗记录id, 结束时间，化疗后评估，治疗所用时长，创建时间，更新时间，创建人，逻辑删除
# 化疗后不良反应表 : id , 化疗后记录表id, 发生时间，简述，处理时间，处理人，处理方式，创建时间，更新时间，创建人，逻辑删除
# 随访信息表 ：id , 医疗记录id,随访频次，随访周期，随访方式，已随访次数，剩余随访次数，下次随访时间，随访状态 随访时间，随访内容，创建时间，更新时间，创建人，逻辑删除
# 随访记录详情表 ： id , 随访信息id, 随访时间，随访内容，创建时间，更新时间，创建人，逻辑删除

-- 创建医疗记录表
CREATE TABLE med_medical_record
(
    record_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '医疗记录的唯一ID',
    patient_id BIGbigint NOT NULL COMMENT '患者ID',
    medical_status tinybigint default '0' NOT NULL COMMENT '医疗状态(0未开始,1进行中,2已结束)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    is_delete   BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (record_id)
) COMMENT = '存储患者的医疗记录';

-- 创建医疗详情表
CREATE TABLE med_medical_detail
(
    medical_record_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '医疗记录的唯一ID',
    record_id BIGbigint NOT NULL COMMENT '医疗记录ID',
    attending_doctor        VARCHAR(255) NOT NULL COMMENT '主治医生姓名',
    department              VARCHAR(255) NOT NULL COMMENT '所属科室',
    location                VARCHAR(255) NOT NULL COMMENT '就诊地点',
    visit_time              TIMESTAMP    NOT NULL COMMENT '就诊时间',
    patient_complabigint    TEXT COMMENT '患者主诉',
    current_disease_history TEXT COMMENT '现病史',
    create_time             TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time             TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by              VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete               BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (medical_record_id)
) COMMENT = '存储医疗记录的详细信息';

-- 创建体征采集情况表
CREATE TABLE med_vital_signs
(
    vital_signs_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '体征采集记录的唯一ID',
    medical_record_id BIGbigint NOT NULL COMMENT '医疗记录ID',
    temperature           DECIMAL(4, 2) COMMENT '体温',
    pulse SMALLbigint COMMENT '脉搏',
    respiration SMALLbigint COMMENT '呼吸频率',
    blood_pressure        VARCHAR(10) COMMENT '血压',
    oxygen_saturation SMALLbigint COMMENT '血氧饱和度',
    weight                DECIMAL(5, 2) COMMENT '体重',
    height                DECIMAL(5, 2) COMMENT '身高',
    bmi                   DECIMAL(5, 2) COMMENT '身体质量指数(BMI)',
    auxiliary_examination TEXT COMMENT '辅助检查结果',
    create_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by            VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete             BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (vital_signs_id)
) COMMENT = '存储体征采集数据';

-- 创建诊断结果表
CREATE TABLE med_diagnosis
(
    diagnosis_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '诊断记录的唯一ID',
    medical_record_id BIGbigint NOT NULL COMMENT '医疗记录ID',
    diagnosis_result    TEXT         NOT NULL COMMENT '诊断结果',
    doctor_instructions TEXT COMMENT '医生指导',
    open_time           TIMESTAMP COMMENT '开放时间',
    follow_up_plan      TEXT COMMENT '随访计划',
    prescription_record TEXT COMMENT '处方记录',
    treatment_plan      TEXT COMMENT '治疗方案',
    create_time         TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by          VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete           BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (diagnosis_id)
) COMMENT = '存储诊断结果和相关计划';

-- 创建化疗记录表
CREATE TABLE med_chemotherapy_record
(
    chemotherapy_record_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '化疗记录的唯一ID',
    medical_record_id BIGbigint NOT NULL COMMENT '医疗记录ID',
    chemotherapy_time    TIMESTAMP    NOT NULL COMMENT '化疗时间',
    prescription_time    TIMESTAMP    NOT NULL COMMENT '处方时间',
    prescriber           VARCHAR(255) NOT NULL COMMENT '处方者',
    on_call_doctor       VARCHAR(255) NOT NULL COMMENT '值班医生',
    start_treatment_time TIMESTAMP    NOT NULL COMMENT '开始治疗时间',
    end_treatment_time   TIMESTAMP    NOT NULL COMMENT '结束治疗时间',
    venous_access        VARCHAR(255) NOT NULL COMMENT '静脉通路',
    create_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by           VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete            BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (chemotherapy_record_id)
) COMMENT = '存储化疗记录';

-- 创建化疗记录体征采集表
CREATE TABLE med_chemotherapy_vital_signs
(
    vital_signs_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '体征采集记录的唯一ID',
    chemotherapy_record_id BIGbigint NOT NULL COMMENT '化疗记录ID',
    temperature           DECIMAL(4, 2) COMMENT '体温',
    pulse SMALLbigint COMMENT '脉搏',
    respiration SMALLbigint COMMENT '呼吸频率',
    blood_pressure        VARCHAR(10) COMMENT '血压',
    oxygen_saturation SMALLbigint COMMENT '血氧饱和度',
    weight                DECIMAL(5, 2) COMMENT '体重',
    height                DECIMAL(5, 2) COMMENT '身高',
    bmi                   DECIMAL(5, 2) COMMENT '身体质量指数(BMI)',
    test_results          TEXT COMMENT '检测结果',
    auxiliary_examination TEXT COMMENT '辅助检查',
    create_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by            VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete             BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (vital_signs_id)
) COMMENT = '存储化疗过程中的体征采集数据';

-- 创建化疗记录当日评估表
CREATE TABLE med_chemotherapy_assessment
(
    chemotherapy_assessment_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '评估记录的唯一ID',
    chemotherapy_record_id BIGbigint NOT NULL COMMENT '化疗记录ID',
    assessment_time    TIMESTAMP    NOT NULL COMMENT '评估时间',
    assessment_content TEXT         NOT NULL COMMENT '评估内容',
    assessing_doctor   VARCHAR(255) NOT NULL COMMENT '评估医生',
    create_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by         VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete          BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (chemotherapy_assessment_id)
) COMMENT = '存储化疗当日的评估记录';

-- 创建化疗记录药物表
CREATE TABLE med_chemotherapy_medication
(
    medication_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '药物记录的唯一ID',
    chemotherapy_record_id BIGbigint NOT NULL COMMENT '化疗记录ID',
    medication_name       VARCHAR(255) NOT NULL COMMENT '药物名称',
    dosage                VARCHAR(255) NOT NULL COMMENT '剂量',
    administration_method VARCHAR(255) NOT NULL COMMENT '给药方式',
    prescriber            VARCHAR(255) NOT NULL COMMENT '处方者',
    create_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by            VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete             BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (medication_id)
) COMMENT = '存储化疗使用的药物信息';

-- 创建化疗后记录表
CREATE TABLE med_post_chemotherapy_record
(
    post_chemotherapy_record_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '化疗后记录的唯一ID',
    chemotherapy_record_id BIGbigint NOT NULL COMMENT '化疗记录ID',
    end_time                     TIMESTAMP    NOT NULL COMMENT '结束时间',
    post_chemotherapy_assessment TEXT         NOT NULL COMMENT '化疗后评估',
    treatment_duration bigintERVAL NOT NULL COMMENT '治疗持续时间',
    create_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by                   VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete                    BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    CONSTRAbigint fk_chemotherapy_record_post_chemotherapy_record FOREIGN KEY (chemotherapy_record_id) REFERENC
) COMMENT = '存储化疗后的记录';

-- 继续创建化疗后不良反应表
CREATE TABLE med_adverse_reaction
(
    adverse_reaction_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '不良反应的唯一ID',
    post_chemotherapy_record_id BIGbigint NOT NULL COMMENT '化疗后记录ID',
    occurrence_time TIMESTAMP                       NOT NULL COMMENT '发生时间',
    description     TEXT                            NOT NULL COMMENT '不良反应描述',
    handling_time   TIMESTAMP                       NOT NULL COMMENT '处理时间',
    handler         VARCHAR(255)                    NOT NULL COMMENT '处理人',
    severity        ENUM ('轻微', '中等', '严重')   NOT NULL COMMENT '严重程度',
    outcome         ENUM ('恢复', '未恢复', '死亡') NOT NULL COMMENT '结果',
    report_time     TIMESTAMP                       NOT NULL COMMENT '报告时间',
    reporter        VARCHAR(255)                    NOT NULL COMMENT '报告人',
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by      VARCHAR(255)                    NOT NULL COMMENT '创建者',
    is_delete       BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (adverse_reaction_id)
) COMMENT = '存储化疗后出现的不良反应记录';

-- 创建随访信息表
CREATE TABLE med_follow_up_info
(
    follow_up_info_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '随访信息ID',
    medical_record_id BIGbigint NOT NULL COMMENT '医疗记录ID',
    frequency       ENUM ('一次', '两次', '三次', '多次')   NOT NULL COMMENT '随访频次',
    cycle           ENUM ('周', '月', '年')                 NOT NULL COMMENT '随访周期单位',
    method          ENUM ('电话', '邮件', '面对面', '在线') NOT NULL COMMENT '随访方式',
    visited_times   bigint                                  NOT NULL DEFAULT 0 COMMENT '已随访次数',
    remaining_times bigint                                  NOT NULL COMMENT '剩余随访次数',
    next_visit_time TIMESTAMP COMMENT '下次随访时间',
    status          ENUM ('未开始', '进行中', '已完成')     NOT NULL COMMENT '随访状态',
    last_visit_time TIMESTAMP COMMENT '最近一次随访时间',
    visit_details   TEXT COMMENT '随访内容',
    create_time     TIMESTAMP                                        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     TIMESTAMP                                        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by      VARCHAR(255)                            NOT NULL COMMENT '创建者',
    is_delete       BOOLEAN                                          DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (follow_up_info_id)
) COMMENT = '存储随访计划和相关信息';

-- 创建随访记录详情表
CREATE TABLE med_follow_up_detail
(
    follow_up_detail_id BIGbigint AUTO_INCREMENT PRIMARY KEY COMMENT '随访记录详情ID',
    follow_up_info_id BIGbigint NOT NULL COMMENT '随访信息ID',
    visit_time  TIMESTAMP    NOT NULL COMMENT '随访时间',
    details     TEXT         NOT NULL COMMENT '随访内容',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    created_by  VARCHAR(255) NOT NULL COMMENT '创建者',
    is_delete   BOOLEAN   DEFAULT FALSE COMMENT '逻辑删除标记',
    primary key (follow_up_detail_id)
) COMMENT = '存储每次随访的具体记录';


# 量表id、量表名称、量表题目、量表分数、量表结果、量表创建时间、量表更新时间、量表创建者、量表删除标记

CREATE TABLE `survey_survey_info`
(
    `id` bigbigint NOT NULL AUTO_INCREMENT,
    `survey_name`        varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主题',
    `survey_description` varchar(1000) CHARACTER SET utf8         DEFAULT NULL COMMENT '描述',
    `survey_sort`        bigint(11)                      NOT NULL DEFAULT '0' COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='调查问卷主表';


CREATE TABLE `survey_question_info`
(
    `id` bigbigint NOT NULL AUTO_INCREMENT,
    `survey_id`      bigint(11)                      NOT NULL COMMENT '关联调查问卷主表ID',
    `question_type`  char(1) CHARACTER SET utf8      NOT NULL DEFAULT '1' COMMENT '1 单选 2滑块 3只显示标题',
    `question_name`  varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '问题主题',
    `question_score` bigint(11)                      NULL COMMENT '问题分数',
    `result`         text                            NULL COMMENT '选项结果',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 212
  DEFAULT CHARSET = utf8mb4 COMMENT ='调查问卷问题主表';


CREATE TABLE `survey_option_info`
(
    `id` bigbigint NOT NULL AUTO_INCREMENT,
    `survey_id`       bigint(11)                      NOT NULL COMMENT '调查问卷ID',
    `question_id`     bigint(11)                      NOT NULL COMMENT '问题ID',
    `option_name`     varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '选项名称',
    `level`           bigint(11)                      NULL COMMENT '选项等级',
    `result`          text                            NULL COMMENT '选项结果',
    `option_score`    bigint(11)                      NULL COMMENT '选项分数',
    `option_max`      bigint(11)                      NULL COMMENT '滑块最大值',
    `option_min`      bigint(11)                      NULL COMMENT '滑块最小值',
    `option_max_name` varchar(255) CHARACTER SET utf8 NULL COMMENT '滑块最大值名称',
    `option_min_name` varchar(255) CHARACTER SET utf8 NULL COMMENT '滑块最小值名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='调查问卷问题选项主表';



CREATE TABLE `answer_info`
(
    `id` bigbigint NOT NULL AUTO_INCREMENT,
    `user_id`     varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '成员id',
    `survey_id`   bigint(11)                      NOT NULL COMMENT '问卷主表ID',
    `question_id` bigint(11)                      NOT NULL COMMENT '问题主表ID',
    `create_date` timestamp                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户答案表';


CREATE TABLE `answer_option_relation`
(
    `id` bigbigint NOT NULL AUTO_INCREMENT,
    `answer_id`      bigint(11) NOT NULL COMMENT '答案主表id',
    `option_id`      bigint(11)                      DEFAULT NULL COMMENT '选项主表id',
    `option_content` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '答案内容',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT bigintO `survey_survey_info` (`survey_name`, `survey_description`, `survey_sort`)
VALUES ('皮肤健康状况调查', '本调查旨在了解您的皮肤健康状况。', 1);

INSERT bigintO `survey_question_info` (`survey_id`, `question_type`, `question_name`)
VALUES (1, '1', '您的皮肤干燥程度如何？');

INSERT bigintO `survey_question_info` (`survey_id`, `question_type`, `question_name`)
VALUES (1, '2', '您对当前皮肤状况的满意度是多少？');

INSERT bigintO `survey_question_info` (`survey_id`, `question_type`, `question_name`)
VALUES (1, '3', '注意事项：请确保每天使用防晒霜。');


INSERT bigintO `survey_option_info` (`survey_id`, `question_id`, `option_name`, `option_score`, `level`)
VALUES (1, 1, '不存在皮肤干燥', 0, 0);

INSERT bigintO `survey_option_info` (`survey_id`, `question_id`, `option_name`, `option_score`, `level`)
VALUES (1, 1, '1级皮肤干燥', 1, 1);

INSERT bigintO `survey_option_info` (`survey_id`, `question_id`, `option_name`, `option_score`, `level`)
VALUES (1, 1, '2-3级皮肤干燥', 2, 2);

INSERT bigintO `survey_option_info` (`survey_id`, `question_id`, `option_name`, `option_max`, `option_min`,
                                  `option_max_name`, `option_min_name`)
VALUES (1, 2, '满意度', 5, 1, '非常满意', '非常不满意');

# 菜单表
CREATE TABLE sys_menu
(
    id bigbigint AUTO_INCREMENT PRIMARY KEY comment '主键ID',
    path        VARCHAR(255) NOT NULL comment '路由地址',
    name        VARCHAR(255) NOT NULL comment '路由名称',
    component   VARCHAR(255) NOT NULL comment '组件路径',
    parent_id   bigint       not null comment '父级ID',
    visible TINYbigint(1) DEFAULT 0 COMMENT '菜单状态（0 显示 1 隐藏）',
    create_by   varchar(255) not null comment '创建者',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_by   varchar(255) DEFAULT NULL comment '更新者',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    icon        VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;



# 医疗记录主表
# id、治疗时间、治疗类型、患者id、创建时间、治疗状态（根据医嘱使用次数制定）、治疗结果
CREATE TABLE cure_medical_records
(
    id bigbigint AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    advice_id bigbigint NOT NULL comment '医嘱id',
    treatment_time   DATETIME     NOT NULL comment '治疗时间',
    treatment_type   VARCHAR(255) NOT NULL comment '治疗类型',
    patient_id bigbigint NOT NULL comment '患者id',
    creation_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    treatment_status VARCHAR(255) comment '治疗状态'
);
INSERT bigintO cure_medical_records (id, advice_id, treatment_time, treatment_type, patient_id, treatment_status)
VALUES (1, 1, '2024-05-28 09:00:00', '化疗', 4005, '结束'), (2, 2, '2024-05-28 10:00:00', '化疗', 4005, '结束'), (3, 2, '2024-05-28 11:00:00', '化疗', 4005, '进行中');
#
# 医疗详情表（根据医嘱）
# id、主表id、接诊医生、接诊科室、接诊地点、接诊时间、患者主诉、现病史、创建日期、创建人
CREATE TABLE cure_medical_details
(
    id bigbigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    medical_record_id bigbigint NOT NULL comment '主表id',
    attending_physician  VARCHAR(255) comment '接诊医生',
    department           VARCHAR(255) comment '接诊科室',
    location             VARCHAR(255) comment '接诊地点',
    attending_time       DATETIME NOT NULL comment '接诊时间',
    patient_complabigint TEXT comment '患者主诉',
    present_illness      TEXT comment '现病史',
    creation_date        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建日期',
    creator              VARCHAR(255) comment '创建人'
);
INSERT bigintO cure_medical_details (medical_record_id, attending_physician, department, location, attending_time,
                                  patient_complabigint, present_illness, creation_date, creator)
VALUES (1, 'Dr. Smith', 'Oncology', 'Room 101', '2024-05-28 08:30:00', 'Chest pain', 'Recent chest pain episodes', '2024-05-28 08:30:00', 'Nurse A'), (2, 'Dr. Johnson', 'Radiology', 'Room 201', '2024-05-28 09:30:00', 'Back pain', 'Chronic back pain', '2024-05-28 09:30:00', 'Nurse B'), (3, 'Dr. Lee', 'Surgery', 'Theater 1', '2024-05-28 10:30:00', 'Abdominal pain', 'Acute abdominal pain', '2024-05-28 10:30:00', 'Nurse C');

# 体征记录表
# id、医疗记录id、体温、血压、血氧、血糖、体重、身高、BMI（根据体重、身高算）、辅助检查内容、创建日期、创建人
CREATE TABLE cure_vitalSigns_records
(
    id bigbigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    medical_record_id bigbigint NOT NULL comment '医疗记录id',
    temperature           FLOAT comment '体温',
    blood_pressure        VARCHAR(20) comment '血压',
    oxygen_saturation     FLOAT comment '血氧',
    blood_sugar           FLOAT comment '血糖',
    weight                FLOAT comment '体重',
    height                FLOAT comment '身高',
    bmi                   FLOAT AS (weight / (height / 100) / (height / 100)) comment 'BMI',
    auxiliary_examination TEXT comment '辅助检查内容',
    creation_date         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建日期',
    creator               VARCHAR(255) comment '创建人'
);
INSERT bigintO cure_vitalSigns_records (medical_record_id, temperature, blood_pressure, oxygen_saturation, blood_sugar,
                                     weight,
                                     height, auxiliary_examination, creation_date, creator)
VALUES (1, 36.8, '120/80', 98, 5.5, 70, 170, 'EKG normal', '2024-05-28 09:10:00', 'Nurse A'), (2, 37.1, '130/85', 99, 6.0, 65, 160, 'X-ray clear', '2024-05-28 10:10:00', 'Nurse B'), (3, 37.0, '135/90', 97, 5.8, 80, 175, 'CT scan pending', '2024-05-28 11:10:00', 'Nurse C');

#
# 诊断结果表
# id、医疗记录表id、初步诊断、医嘱、开方时间、随访计划、处方记录、治疗计划、创建日期、创建人
CREATE TABLE cure_diagnosis_records
(
    id bigbigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    medical_record_id bigbigint NOT NULL comment '医疗记录表id',
    preliminary_diagnosis TEXT comment '初步诊断',
    prescription          TEXT comment '医嘱',
    prescription_time     DATETIME comment '开方时间',
    follow_up_plan        TEXT comment '随访计划',
    prescription_record   TEXT comment '处方记录',
    treatment_plan        TEXT comment '治疗计划',
    creation_date         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建日期',
    creator               VARCHAR(255)
);
INSERT bigintO cure_diagnosis_records (medical_record_id, preliminary_diagnosis, prescription, prescription_time, follow_up_plan,
                              prescription_record, treatment_plan, creation_date, creator)
VALUES (1, 'Suspected tumor', 'Antibiotics', '2024-05-28 09:20:00', 'Weekly for 4 weeks', 'Antibiotic course', 'Chemo every 2 weeks', '2024-05-28 09:20:00', 'Dr. Smith'), (2, 'Degenerative disc', 'Pain killers', '2024-05-28 10:20:00', 'Monthly', 'Pain management', 'Physical therapy', '2024-05-28 10:20:00', 'Dr. Johnson'), (3, 'Appendicitis', 'Surgery prep', '2024-05-28 11:20:00', 'As needed', 'Surgical bigintervention', 'Immediate surgery', '2024-05-28 11:20:00', 'Dr. Lee');

# 化疗记录主表
# id、医疗记录表id、化疗时间、方案开局时间、开方人、值班医生、静脉通路
CREATE TABLE cure_chemotherapy_records
(
    id                bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    medical_record_id bigint   NOT NULL comment '医疗记录表id',
    chemotherapy_time DATETIME NOT NULL comment '化疗时间',
    prescription_time DATETIME comment '方案开局时间',
    prescriber        VARCHAR(255) comment '开方人',
    on_duty_physician VARCHAR(255) comment '值班医生',
    iv_access         TEXT comment '静脉通路'
);
-- 插入化疗记录主表数据
INSERT INTO cure_chemotherapy_records (medical_record_id, chemotherapy_time, prescription_time, prescriber,
                                       on_duty_physician,
                                       iv_access)
VALUES (1, '2024-05-28 09:00:00', '2024-05-27 15:00:00', 'Dr. Smith', 'Dr. Brown', 'Inserted'),
       (2, '2024-05-29 10:00:00', '2024-05-28 16:00:00', 'Dr. Johnson', 'Dr. Black', 'Not inserted');

# 化疗当日体征检测表
# id、化疗记录主表id、体温、血压、血氧、血糖、体重、身高、BMI（根据体重、身高算）、检查结果（是否可以进行治疗）、治疗开始时间、治疗结束时间
CREATE TABLE cure_chemotherapy_daily_signs
(
    id                     bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    chemotherapy_record_id bigint NOT NULL comment '化疗记录主表id',
    temperature            FLOAT comment '体温',
    blood_pressure         VARCHAR(20) comment '血压',
    oxygen_saturation      FLOAT comment '血氧',
    blood_sugar            FLOAT comment '血糖',
    weight                 FLOAT comment '体重',
    height                 FLOAT comment '身高',
    bmi                    FLOAT AS (weight / (height / 100) / (height / 100)) comment 'BMI',
    examination_result     TEXT comment '检查结果',
    treatment_start_time   DATETIME comment '治疗开始时间',
    treatment_end_time     DATETIME comment '治疗结束时间'
);
INSERT INTO cure_chemotherapy_daily_signs (chemotherapy_record_id, temperature, blood_pressure, oxygen_saturation,
                                           blood_sugar,
                                           weight, height, examination_result, treatment_start_time, treatment_end_time)
VALUES (1, 36.9, '125/85', 99, 5.6, 70, 170, 'Clear to proceed', '2024-05-28 09:30:00', '2024-05-28 12:30:00'),
       (2, 37.0, '135/95', 100, 6.2, 65, 160, 'Clear to proceed', '2024-05-29 10:30:00', '2024-05-29 13:30:00');


# 化疗单日治疗药物表
# id、化疗记录主表id、药品名称、规格、数量、开方人
CREATE TABLE cure_chemo_daily_medication
(
    id                     bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    chemotherapy_record_id bigint NOT NULL comment '化疗记录主表id',
    medication_name        VARCHAR(255) comment '药品名称',
    specification          VARCHAR(255) comment '规格',
    quantity               bigint comment '数量',
    prescriber             VARCHAR(255) comment '开方人'
);

-- 插入化疗单日治疗药物表数据
INSERT INTO cure_chemo_daily_medication (chemotherapy_record_id, medication_name, specification, quantity, prescriber)
VALUES (1, 'Cisplatin', '50mg', 1, 'Dr. Smith'),
       (2, 'Gemcitabine', '1000mg', 1, 'Dr. Johnson');
#
# 治疗后信息表
# id、医疗记录id、结束时间、化疗后评估、治疗所用时长、创建时间、创建人
CREATE TABLE cure_post_treatment_info
(
    id                      bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    medical_record_id       bigint   NOT NULL comment '医疗记录id',
    end_time                DATETIME comment '结束时间',
    chemotherapy_assessment TEXT comment '化疗后评估',
    treatment_duration      TIME comment '治疗所用时长',
    creation_date           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    creator                 VARCHAR(255) comment '创建人'
);

-- 插入治疗后信息表数据
INSERT INTO cure_post_treatment_info (medical_record_id, end_time, chemotherapy_assessment, treatment_duration,
                                      creation_date,
                                      creator)
VALUES (1, '2024-05-28 12:30:00', 'Good response', '03:00:00', '2024-05-28 12:35:00', 'Nurse A'),
       (2, '2024-05-29 13:30:00', 'Moderate response', '03:00:00', '2024-05-29 13:35:00', 'Nurse B');

# 不良反应表
# id、治疗后信息id、发生时间、介绍、处理时间、处理人

-- 创建不良反应表
CREATE TABLE cure_adverse_reactions
(
    id                     bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    post_treatment_info_id bigint NOT NULL comment '治疗后信息id',
    occurrence_time        DATETIME comment '发生时间',
    description            TEXT comment '介绍',
    management_time        DATETIME comment '处理时间',
    manager                VARCHAR(255) comment '处理人'
);
-- 插入不良反应表数据
INSERT INTO cure_adverse_reactions (post_treatment_info_id, occurrence_time, description, management_time, manager)
VALUES (1, '2024-05-28 12:45:00', 'Nausea after treatment', '2024-05-28 13:00:00', 'Nurse A');

#
# 随访信息表
# id、医疗记录表id、患者id、随访频次、随访周期、随访方式、已随访次数、剩余随访次数、下次随访时间、随访状态

CREATE TABLE cure_follow_up_info
(
    id                        bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    medical_record_id         bigint NOT NULL comment '医疗记录表id',
    patient_id                bigint NOT NULL comment '患者id',
    follow_up_frequency       bigint comment '随访频次',
    follow_up_period          VARCHAR(255) comment '随访周期',
    follow_up_method          VARCHAR(255) comment '随访方式',
    follow_up_times           bigint comment '已随访次数',
    remaining_follow_up_times bigint comment '剩余随访次数',
    next_follow_up_date       DATETIME comment '下次随访时间',
    follow_up_status          VARCHAR(255) comment '随访状态'
);
-- 插入随访信息表数据
INSERT INTO cure_follow_up_info (medical_record_id, patient_id, follow_up_frequency, follow_up_period, follow_up_method,
                                 follow_up_times, remaining_follow_up_times, next_follow_up_date, follow_up_status)
VALUES (1, 1, 1, 'Weekly', 'Phone', 1, 3, '2024-06-04 10:00:00', 'Active'),
       (2, 2, 1, 'Bi-weekly', 'Email', 2, 2, '2024-06-18 10:00:00', 'Active');
#
# 随访信息详情表
# id、随访信息表id、信息发送时间、发送方式、发送状态、随访完成时间
CREATE TABLE cure_follow_up_info_details
(
    id                        bigint AUTO_INCREMENT PRIMARY KEY comment '主键id',
    follow_up_info_id         bigint NOT NULL comment '随访信息表id',
    information_send_time     DATETIME comment '信息发送时间',
    send_method               VARCHAR(255) comment '发送方式',
    send_status               VARCHAR(255) comment '发送状态',
    follow_up_completion_time DATETIME comment '随访完成时间'
);
-- 插入随访信息详情表数据
INSERT INTO cure_follow_up_info_details (follow_up_info_id, information_send_time, send_method, send_status,
                                         follow_up_completion_time)
VALUES (1, '2024-05-29 10:00:00', 'Phone', 'Sent', '2024-05-29 10:10:00'),
       (2, '2024-05-30 15:00:00', 'Email', 'Sent', '2024-05-30 15:20:00');


-- 创建医嘱治疗方案主表
CREATE TABLE `cure_treatment_plan_main`
(
    `id`                  BIGINT AUTO_INCREMENT PRIMARY KEY comment '主键id',
    `advice_id`           BIGINT       NOT NULL comment '医嘱id',
    `start_date`          DATE         NOT NULL comment '开始日期',
    `expected_end_date`   DATE,
    `treatment_cycle`     VARCHAR(255) NOT NULL comment '治疗周期',
    `treatment_frequency` VARCHAR(255) NOT NULL comment '治疗频率',
    `prescribing_doctor`  VARCHAR(255) NOT NULL comment ' prescribing_doctor'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
INSERT INTO `cure_treatment_plan_main`
(`advice_id`, `start_date`, `expected_end_date`, `treatment_cycle`, `treatment_frequency`, `prescribing_doctor`)
VALUES (1, '2024-01-01', '2024-03-01', '30天', '每日一次', '张三'),
       (2, '2024-02-01', '2024-04-01', '60天', '隔日一次', '李四');

-- 创建治疗治疗方案详情表
CREATE TABLE `cure_treatment_plan_details`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY comment '主键id',
    `medical_order_id` BIGINT       NOT NULL comment '治疗方案主表id',
    `first_use_time`   DATETIME,
    `total_times`      INT          NOT NULL comment '总次数',
    `used_times`       INT          NOT NULL comment '已使用次数',
    `usage_status`     VARCHAR(255) NOT NULL comment '使用状态'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
INSERT INTO `cure_treatment_plan_details`
(`medical_order_id`, `first_use_time`, `total_times`, `used_times`, `usage_status`)
VALUES (1, '2024-01-02 09:00:00', 30, 10, '进行中'),
       (2, '2024-02-02 10:00:00', 60, 20, '暂停');

-- 创建治疗方案药品详情表
CREATE TABLE `cure_treatment_plan_medicine_details`
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY comment '主键id',
    `detail_id`     BIGINT       NOT NULL comment '治疗方案详情表id',
    `medicine_name` VARCHAR(255) NOT NULL comment '药品名称',
    `specification` VARCHAR(255) comment '规格',
    `quantity`      INT          NOT NULL comment '数量',
    `prescriber`    VARCHAR(255) NOT NULL comment '开方人'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
INSERT INTO `cure_treatment_plan_medicine_details`
(`detail_id`, `medicine_name`, `specification`, `quantity`, `prescriber`)
VALUES (1, '药品A', '100mg', 50, '张三'),
       (1, '药品B', '200mg', 30, '张三'),
       (2, '药品C', '150mg', 45, '李四');


CREATE TABLE `cure_treatment_records`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    `treatment_type`   VARCHAR(255) NOT NULL COMMENT '治疗类型',
    `medical_order_id` BIGINT       NOT NULL COMMENT '医嘱id',
    `treatment_status` VARCHAR(255) NOT NULL COMMENT '治疗状态',
    `treatment_result` VARCHAR(255) COMMENT '治疗结果',
    `creation_date`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `patient_id`       BIGINT       NOT NULL COMMENT '患者id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `cure_treatment_records`
(`treatment_type`, `medical_order_id`, `treatment_status`, `treatment_result`, `patient_id`)
VALUES ('化疗', 1001, '进行中', '良好', 2001)


create table appointment_room
(
    id               bigint auto_increment comment '输液室id'
        primary key,
    transfusion_name varchar(30)      null comment '输液室名称',
    medical_type     char default ''  null comment '患者类型(1急诊,2门诊)',
    status           char default '0' null comment '输液室状态（0正常 1停用）',
    Infusion_site    varchar(255)     null comment '输液地点',
    num_site         varchar(255)     null comment '取号点',
    phone            varchar(255)     null comment '联系电话',
    message          text             null comment '短信回复内容'
)
    comment '输液室表';

create table appointment_seat
(
    id             bigint auto_increment comment '座位id'
        primary key,
    seat_number    varchar(10)      not null comment '座位号',
    transfusion_id bigint           not null comment '大厅id',
    medical_type   char default ''  not null comment '患者类型(1急诊,2门诊)',
    status         char default '0' null comment '输液室状态（0正常 1停用）'
)
    comment '座位表';


create table appointment_dept
(
    dept_id     bigint auto_increment comment '科室id'
        primary key,
    dept_name   varchar(30) default ''  null comment '科室名称',
    parent_id   bigint      default 0   null comment '父科室id',
    dept_sign   int                     not null comment '科室标记(0普通科室,1输液科室)',
    status      char        default '0' null comment '部门状态（0正常 1停用）',
    create_by   varchar(64) default ''  null comment '创建者',
    create_time datetime                null comment '创建时间',
    update_by   varchar(64) default ''  null comment '更新者',
    update_time datetime                null comment '更新时间'
)
    comment '科室表';

-- auto-generated definition
create table appointment_config
(
    config_id      bigint auto_increment comment '配置id'
        primary key,
    time_class     char             not null comment '时间类型（0上午，1下午）',
    start_time     time             not null comment '开始时间',
    end_time       time             not null comment '结束时间',
    source_number  int              not null comment '号源数量',
    standby_number int              not null comment '临时号源',
    medical_type   varchar(10)      null comment '患者类型(1急诊,2门诊)',
    week_id        char             not null comment '星期时间',
    status         char default '0' null comment '号源配置状态',
    transfusion_id int              not null comment '输液室id'
)
    comment '号源模板主表';

-- auto-generated definition
create table appointment_config_dept
(
    config_id bigint not null comment '配置id',
    dept_id   bigint not null comment '科室id',
    primary key (config_id, dept_id)
)
    comment '号源配置科室表';

-- auto-generated definition
create table appointment_source
(
    source_id       bigint auto_increment comment 'id'
        primary key,
    config_date     date             not null comment '日期',
    start_time      time             not null comment '开始时间',
    end_time        time             not null comment '结束时间',
    source_total    int              not null comment '号源总数',
    remaining_count int              not null comment '剩余数量',
    config_id       bigint           not null comment '配置id',
    status          char default '0' null comment '每日号源状态',
    standby_number  int              not null comment '临时号源',
    transfusion_id  int              null comment '输液室id'
)
    comment '号源表';

create table apt_drug
(
    drug_id            bigint auto_increment comment '药品id'
        primary key,
    res_id             bigint       not null comment '记录id',
    register_id        varchar(255) null comment '流水号',
    infusion_apply_nos text         not null comment '输液单号拼接',
    djsj               varchar(255) null comment '药品创建时间',
    create_doctor      varchar(255) null comment '开方医生'
)
    comment '预约药品记录';

create table apt_drug_details
(
    Sjly    varchar(50)             null,
    Cfsbh   varchar(255)            null,
    Ds      text                    null,
    Gg      varchar(255)            null,
    Id      int auto_increment
        primary key,
    Jl      varchar(100)            null,
    Pd      varchar(20)             null,
    Psxx    tinyint      default 0  null,
    Ypmc    varchar(255)            null,
    Z_id    int                     null,
    cfls    varchar(50)             null,
    kkcbz   tinyint      default 0  null,
    fysycs  tinyint      default 0  null,
    Dsybz   tinyint      default 0  null,
    Sl      int                     null,
    Psjg    int          default 0  null,
    yf      tinyint                 null,
    ypid    varchar(255)            null,
    ypcs    tinyint                 null,
    sydh    varchar(50)             null,
    sycs    tinyint      default 0  null,
    bzjl    varchar(255) default '' null,
    yfms    varchar(50)             null,
    kssbz   tinyint      default 0  null,
    ylts    varchar(255) default '' null,
    pcdm    tinyint                 null,
    yytj    varchar(20)             null,
    kbbz    varchar(2)              null,
    jpid    int          default 0  null,
    ps      varchar(20)             null,
    bz      varchar(50)  default '' null,
    zby     tinyint(1)   default 0  null,
    sfzt    tinyint      default 1  null,
    Dose    varchar(50)             null,
    Unit    varchar(50)             null,
    sysc    int                     null,
    drug_id varchar(255)            null comment '药品id'
);


CREATE TABLE appointment_details
(
    id            BIGINT primary key AUTO_INCREMENT COMMENT '主键ID',
    appointee     VARCHAR(255) COMMENT '预约人',
    card_number   VARCHAR(255) COMMENT '预约卡号',
    appoint_date  DATE COMMENT '预约日期',
    seat_id       bigint COMMENT '预约座位id',
    seat_number   VARCHAR(255) COMMENT '座位号',
    appoint_time  TIME COMMENT '预约时间',
    patient_type  VARCHAR(50) COMMENT '患者类型',
    create_time   DATETIME COMMENT '创建时间',
    update_time   DATETIME COMMENT '修改时间',
    source_type   VARCHAR(50) COMMENT '号源类型',
    operator_id   BIGINT COMMENT '操作人id',
    operator_name VARCHAR(255) COMMENT '操作人名称',
    infusion_id   bigint COMMENT '输液室id',
    infusion_name VARCHAR(255) COMMENT '输液室名称',
    phone_number  VARCHAR(255) COMMENT '患者手机号',
    source_id     BIGINT COMMENT '所属号源id',
    status        TINYINT COMMENT '预约状态（0已预约、1人工取消、2患者取消）',
    queue_number  INT COMMENT '排队号',
    serial_number BIGINT COMMENT '流水号',
    is_delete     TINYINT DEFAULT 0
) COMMENT ='预约信息表';

create table sys_room_dept
(
    dept_id        bigint not null comment '科室id',
    room_id bigint not null comment '输液室id',
    primary key (room_id, dept_id)
)
    comment '输液室分配科室表';

