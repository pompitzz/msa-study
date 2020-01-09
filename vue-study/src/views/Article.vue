<template>
    <div>
        <v-container>
            <h1 class="my-5 text-center">ES5와 ES6</h1>
            <v-card class="pa-3" light>
                <viewer :value="editorText" dark="false"/>
            </v-card>
        </v-container>
    </div>
</template>

<script>
    import Viewer from '@toast-ui/vue-editor/src/Viewer.vue'

    export default {
        name: "Article",
        data() {
            return {
                editorText: '### ES5와 ES6\n' +
                    '#### ES5\n' +
                    '- 선언한 함수와 변수를 해석기가 가장 상단에 있는 것처럼 인식\n' +
                    '- js 해석기는 코드의 라인 순서와 관계없이 함수 선언식과 변수를 위한 메모리 공간을 먼저 확보\n' +
                    '- 따라서 function (), var 코드는 최상단에 끌어 올려진 \n' +
                    '것 처럼보이기 때문에 변수를 뒤늦게 작성해도 괜찮은 것\n' +
                    '\n' +
                    '![title](https://image-spring.s3.ap-northeast-2.amazonaws.com/static/codedeploy.png ){: width="100%" height="100%"}\n' +
                    '\n' +
                    '\n' +
                    '```js\n' +
                    '// 함수 선언 식\n' +
                    'function sum(a, b) {\n' +
                    ' return a + b;\n' +
                    '};\n' +
                    '\n' +
                    '// 함수 표현 식\n' +
                    'var sum = function() {\n' +
                    'return 10 + 20;\n' +
                    '}\n' +
                    '```\n' +
                    '```js\n' +
                    '// 이렇게 코딩하고 실행을 해보면\n' +
                    'function a() {\n' +
                    '  return 10;\n' +
                    '}\n' +
                    '\n' +
                    'a();\n' +
                    '\n' +
                    'function a(){\n' +
                    ' return 5;\n' +
                    '}\n' +
                    '\n' +
                    '// 실제론 이렇게 컴파일 되기 때문에 5가 리턴된다.\n' +
                    'function a() {\n' +
                    '  return 10;\n' +
                    '}\n' +
                    'function a(){\n' +
                    '  return 5;\n' +
                    '}\n' +
                    '\n' +
                    'a();\n' +
                    '```\n' +
                    '``` js\n' +
                    '// 이렇게 선언하더라도 에러가 나지 않음\n' +
                    'var sum = 5;\n' +
                    'sum = sum + i;\n' +
                    'var i = 10;\n' +
                    '\n' +
                    '// 실제론 이렇게 컴파일 되기 때문에 값이 이상하겠지만 정상적으로 동작한다.\n' +
                    'var sum;\n' +
                    'var i;\n' +
                    'sum = 5;\n' +
                    'sum = sum + i;\n' +
                    'i = 10;\n' +
                    '```\n' +
                    '#### ES6\n' +
                    '- 블록단위 { }로 변수의 범위가 제한된다.\n' +
                    '- const: 한번 선언한 값에 대해 변경 불가 ( final )\n' +
                    '- let: 한번 선언한 값에 대해서 다시 선언할 수 없음. (int a후 다시 int a 불가)\n' +
                    '```js\n' +
                    '//var 예제\n' +
                    'var sum = 0;\n' +
                    'for(var i = 1; i < 5; i++){\n' +
                    '  sum += i;\n' +
                    '}\n' +
                    'console.log(sum); // 10;\n' +
                    'console.log(i); // 5\n' +
                    '```\n' +
                    '```js\n' +
                    '//let 예제\n' +
                    'let sum = 0;\n' +
                    'for(let i = 1; i < 5; i++){\n' +
                    '  sum += i;\n' +
                    '}\n' +
                    'console.log(sum); // 10;\n' +
                    'console.log(i); // 에러 발생, { }이 변수의 범위이므로\n' +
                    '```\n' +
                    '```js\n' +
                    '//const 예제\n' +
                    'const a = 20;\n' +
                    'a = 10; // 에러 발생\n' +
                    '\n' +
                    '// 하지만 객체나 배열의 내부는 변경 가능하다\n' +
                    'const a = {};\n' +
                    'a.num = 10;\n' +
                    'a; // {num: 10};\n' +
                    'const b =  [];\n' +
                    'b.push(10);\n' +
                    'b; // [10]\n' +
                    '```\n' +
                    '#### Arrow Function - ES6 화살표 함수\n' +
                    '- 함수를 정의할 때 function 이라는 키워드를 사용하지 않고 => 로 대체\n' +
                    '- 흔히 사용하는 콜백 함수의 문법을 간결과\n' +
                    '```js\n' +
                    '// ES5 함수 정의 방식\n' +
                    'var sum = function(a, b){\n' +
                    ' return a + b;\n' +
                    '}\n' +
                    '\n' +
                    '// ES6 함수 정의 방식\n' +
                    'var sum = (a, b) => {\n' +
                    ' return a + b;\n' +
                    '}\n' +
                    '```\n' +
                    '```js\n' +
                    '// ES5\n' +
                    'var arr = [\'a\', \'b\', \'c\'];\n' +
                    'arr.forEach(function(value){\n' +
                    ' console.log(value);\n' +
                    '});\n' +
                    '\n' +
                    '// ES6: 웹페이지의 바이트수가 줄어드니 조금의 성능 이윤은 있으나 그거보단 간결해진게 좋다\n' +
                    'var arr = [\'a\', \'b\', \'c\'];\n' +
                    'arr.forEach(value => console.log(value));\n' +
                    '```\n' +
                    '#### Enhanced Object Literals - 향상된 객체 리터럴\n' +
                    '- 객체의 속성을 메서드로 사용할 때 function 예약어를 생략하고 생성 가능\n' +
                    '```js\n' +
                    '// ES5\n' +
                    'var dic = {\n' +
                    ' lookup: function() {\n' +
                    '   console.log(\'hi lookup\');\n' +
                    ' }\n' +
                    '};\n' +
                    'dic.lookup();\n' +
                    '\n' +
                    '// ES6의 향상된 객체 리터럴\n' +
                    'var dic = {\n' +
                    ' lookup(){ // function이 필요없어짐\n' +
                    '   console.log(\'hi lookup\');\n' +
                    ' }\n' +
                    '};\n' +
                    'dic.lookup();\n' +
                    '```\n' +
                    '```js\n' +
                    'var figures = 10;\n' +
                    'var dic = {\n' +
                    ' // figures: figures, -> 객체의 속성과 값의 name이 같을땐 생략 가능\n' +
                    ' figures\n' +
                    '}\n' +
                    '```\n'
            }
        },
        components: {
            Viewer
        }
    }
</script>

<style scoped>

</style>