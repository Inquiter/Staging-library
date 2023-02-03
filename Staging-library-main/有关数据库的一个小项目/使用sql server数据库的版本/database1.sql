create database database1;
use database1;

create table users(id int identity(1,1) primary key,username varchar(100),password varchar(100),birthday datetime,idcard varchar(20),account_type varchar(1));
select * from users;

insert into users values('张三','123','2000-1-1 00:00:00','111111111111111111','0');
insert into users values('李四','456','2000-1-1 00:00:00','111111111111111112','1');
drop table users;

create table examination(id int identity(1,1) primary key,examination_subjects varchar(100),examination_introduction varchar(1000),examination_information varchar(1000),examination_requirements varchar(1000),examination_content varchar(1000),examination_time datetime,examination_place varchar(100));
select * from examination;

insert into examination values('普通话一级考试',
'1.普通话一级分为一级甲等和一级乙等。 2.标准如下：一级甲等：朗读和自由交谈时，语音标准，语汇、语法正确无误，语调自然，表达流畅，测试总失分率在百分之三以内，一级乙等：朗读和自由交谈时，语音标准，语汇、语法正确无误，语调自然，表达流畅。 3.偶有字音、字调失误。 4.测试总失分率在百分之八以内。 5.普通话水平测试试卷由以下四个测试项构成，总分为100分，读单音节字词100个，限时3分30秒，占10分。 6.读双音节词语50个，限时2分30秒，占20分。',
'中国汉语水平考试等级之一。属基础水平。指具有基础(低)汉语能力,能理解简单的语句,表达简单的意思,可进行日常生活、学习方面的初步语言交际。掌握600个左右汉语常用词和与之相应的语法项目的汉语学习者可达到这一标准。',
'普通话证报名要求是年龄要满十八岁，个别的可以放宽至十六周岁，然后是拥有我国的国籍，并且是合法的公民。只要是符合这些要求，便可以报名参加考试，考试通过后，可取得普通话证书。',
'1、读单音节字词：100个词需要在3.5分钟内读完，借由这项考试内容，能考察到考生对于声母、韵母、声调读音的掌握程度。 2、读双音节词语：这项考试的时间是2.5分钟，需要读完50个词组，并在这一过程中测查考生是否熟练掌握了声母、韵母、声调、变调、轻声、儿化读音。 3、短文朗读：一般短文当中的音节是有400个，需要4分钟内读完，借此可以考察考生的读音标准程度，以及连读音变、停连、语调等方面的情况。 4、命题说话：进入这项考试会有3分钟的时间，根据命题在无字的情况下说一段话，重点考察的是考生的读音标准程度，以及语言的自然流畅程度等等。',
'2022-12-20 08:00:00',
'1号楼1209');
insert into examination values('普通话二级考试',
'甲等（87—91.9分） 朗读和自由交谈时，声韵调发音基本标准，语调自然，表达流畅。少数难点音（平翘舌音、前后鼻尾音、边鼻音等）有时出现失误。词语、语法极少有误。测试总失分率在13%以内。 乙等（80—86.9分） 朗读和自由交谈时，个别调值不准，声韵母发音有不到位现象。难点音（平翘舌音、前后鼻尾音、边鼻音、fu-hu、z-zh-j、送气不送气、i-ü不分，保留浊塞音和浊塞擦音、丢介音、复韵母单音化等）失误较多。方言语调不明显。有使用方言词、方言语法的情况。测试总失分率在 20%以内。',
'考试形式为口试。普通话水平测试不是口才的评定，而是对应试人掌握和运用普通话所达到的规范程度的测查和评定，是应试人的汉语标准语测试。应试人在运用普通话口语进行表达过程中所表现的语音、词汇、语法规范程度，是评定其所达到的水平等级的重要依据。',
'普通话证报名要求是年龄要满十八岁，个别的可以放宽至十六周岁，然后是拥有我国的国籍，并且是合法的公民。只要是符合这些要求，便可以报名参加考试，考试通过后，可取得普通话证书。',
'1、读单音节字词：100个词需要在3.5分钟内读完，借由这项考试内容，能考察到考生对于声母、韵母、声调读音的掌握程度。 2、读双音节词语：这项考试的时间是2.5分钟，需要读完50个词组，并在这一过程中测查考生是否熟练掌握了声母、韵母、声调、变调、轻声、儿化读音。 3、短文朗读：一般短文当中的音节是有400个，需要4分钟内读完，借此可以考察考生的读音标准程度，以及连读音变、停连、语调等方面的情况。 4、命题说话：进入这项考试会有3分钟的时间，根据命题在无字的情况下说一段话，重点考察的是考生的读音标准程度，以及语言的自然流畅程度等等。',
'2022-12-22 08:00:00',
'1号楼1212');
drop table examination;

create table roll(id int identity(1,1) primary key,username varchar(100),idcard varchar(20),registration_time datetime,examination_subjects varchar(100),examination_time datetime,examination_place varchar(100),examination_fee varchar(100),payment_method varchar(100),payment_status varchar(100));
select * from roll;

drop table roll;
