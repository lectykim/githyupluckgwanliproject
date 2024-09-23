const emailCheck = (email)=>{
    const emailRegex = /^(?=.{1,254}$)(?=.{1,64}@.{1,255}$)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    return emailRegex.test(email);
}
const checkPassword = (password) =>{
    /*
    * ^(?=.*[A-Za-z]): 최소 1개의 영어 알파벳이 포함되어야 함 (대문자 또는 소문자).
        (?=.*\d): 최소 1개의 숫자가 포함되어야 함.
        (?=.*[!@#$%^&*()]): 최소 1개의 지정된 특수문자가 포함되어야 함 (!@#$%^&*()).
        [A-Za-z\d!@#$%^&*()]{8,31}: 패스워드가 허용된 문자(영어 대소문자, 숫자, 특수문자)로만 구성되어 있으며, 8자 이상 31자 이하인지 확인합니다.
        32자가 미만이어야 하므로 31자로 설정했습니다.
        ^와 $: 패턴의 시작과 끝을 의미하여, 패스워드 전체가 이 조건을 만족하도록 합니다.
    * */
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()])[A-Za-z\d!@#$%^&*()]{8,31}$/;

    return passwordRegex.test(password);
}

const isNotEmpty = (data) =>{
    return !(data === "" || data === null || data === undefined);
}

const checkTitle = (title) =>{
    return title.length < 255;
}

const checkMaxMember = (maxMember)=>{
    return maxMember<5000;
}