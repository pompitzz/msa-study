/**
 * 현재 사용하지 않는 코드들
 */

// function uploadFormData(image) {
//     const data = new FormData();
//     image.forEach(i => data.append('images', i));
//     data.append('name', 'james');
//     data.append('email', 'wqdwq@gamil.com');
//     return data;
// }

// function uploadImage(image) {
//     const data = uploadFormData(image);
//     const requestData = {
//         url: `${process.env.VUE_APP_BASEURL}/upload`,
//         data: data,
//         method: 'POST',
//         headers: {
//             'Content-Type': 'multipart/form-data'
//         }
//     };
//     return axios(requestData);
// }