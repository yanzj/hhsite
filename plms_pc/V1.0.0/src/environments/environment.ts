// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  server: location.protocol + '/plms'//打包地址
  // server: 'http://127.0.0.1:9080' + '/plms'//本地测试地址
  //  server: 'http://192.168.0.154:9080' + '/plms'//本地测试地址
  //   server: 'http://192.168.0.164:9080' + '/plms' //本地测试地址
  // server: 'http://192.168.0.123:8086' + '/plms' //本地测试地址
  // server: 'http://192.168.0.4:8080' + '/nlbs' //本地测试地址
};

