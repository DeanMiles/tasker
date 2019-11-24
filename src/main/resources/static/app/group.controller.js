(function () {
    'use strict';

    angular
        .module('app')
        .controller('GroupController', GroupController);

    GroupController.$inject = ['$http'];

    function GroupController($http) {
        var vm = this;

        vm.groups = [];
        vm.getAll = getAll;
        // vm.getDone = getDone;
        // vm.getUnDone = getUnDone;
        // vm.toggledone = toggledone;
        // vm.deleteTask = deleteTask;


        init();

        function init(){
            getAll();
        }

        // function getUnDone() {
        //     var url = "/groups/showUnDoneOnly";
        //     $http.get(url).then(function (response) {
        //         vm.tasks = response.data;
        //     });
        // }
        //
        // function getDone() {
        //     var url = "/groups/showDoneOnly";
        //     $http.get(url).then(function (response) {
        //        vm.tasks = response.data;
        //     });
        // }
        //
        // function deleteTask(id){
        //     var url = "/groups/delete/" + id;
        //     $http.post(url).then(function (response) {
        //        vm.tasks = response.data;
        //     });
        // }
        //
        // function toggledone(id) {
        //     var url = "/groups/toggleDone/" + id;
        //     $http.post(url).then(function (response) {
        //        vm.tasks = response.data;
        //     });
        // }

        function getAll(){
            var url = "/groups/mygroups";
            var groupsPromise = $http.get(url);
            groupsPromise.then(function(response){
                vm.groups = response.data;
            });
        }
    }
})();
