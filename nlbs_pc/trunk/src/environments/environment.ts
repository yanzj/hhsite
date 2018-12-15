// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  server: location.protocol + '/nlbs'//打包地址
  // server: 'http://192.168.0.4:8080' + '/nlbs'   //本地测试地址
  // server: 'http://192.168.0.128:8080' + '/nlbs'   //本地测试地址
};

