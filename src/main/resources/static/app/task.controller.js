(function () {
    'use strict';

    angular
        .module('app')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['$http'];

    function TaskController($http) {
        var vm = this;

        var dones = [0, 0, 0];
        var donesBtn = [document.getElementById("getBothdonebtn"), document.getElementById("getDonebtn"), document.getElementById("getUndonebtn")];

        var times = [0, 0, 0, 0];
        var timesBtn = [document.getElementById("getAlltimebtn"), document.getElementById("getWeekbtn"), document.getElementById("getMonthbtn"), document.getElementById("getYearbtn")];

        var groups = [0, 0, 0];
        var groupsBtn = [document.getElementById("getAlltasksbtn"), document.getElementById("getMytasksbtn"), document.getElementById("getGrouptasksbtn")];

        vm.tasks = [];
        vm.setDone = setDone;
        vm.setTime = setTime;
        vm.setGroup = setGroup;
        vm.toggle = toggle;
        vm.deleteTask = deleteTask;
        vm.addTask = addTask;
        vm.test = test;
        vm.addOwner = addOwner;
        vm.deleteOwner = deleteOwner;
        vm.deleteTask = deleteTask;
        vm.ownersJson;
        vm.getOwners = getOwners;
        vm.owners;


        init();

        function init() {
            setActive(0, 0, 0);
        }

        function addOwner() {
            var task = document.getElementById("newOwnerselect").value;
            var user = document.getElementById("newOwnerName").value;
            var url = "/tasks/addOwner/" + task + "/" + user;
            $http.post(url).then(function () {
                setUpTasks();
            });
        }

        function getOwners() {
            var select = document.getElementById("deleteOwnerTaskselect").value;
            for (var i = 0; i < vm.tasks.length; i++) {
                if (vm.tasks[i].id === Number(select)) {
                    vm.owners = test(vm.tasks[i].ownerIds);
                }
            }
        }

        function deleteOwner() {
            var task = Number(document.getElementById('deleteOwnerTaskselect').value);
            var user = document.getElementById('deleteOwnerselect').value;
            var url = "/tasks/deleteOwner/" + task + "/" + user;
            $http.post(url).then(function () {
                setUpTasks();
            });
        }

        function test(id) {
            var string = id.toString().split(",");
            var names = [];
            for (var i = 0; i < string.length; i++) {
                if (vm.ownersJson[string[i]] != null) {
                    names.push(vm.ownersJson[string[i]]);
                }
            }
            return names;
        }

        function addTask() {
            var title = document.getElementById('titleInput').value;
            var date = document.getElementById('endDate').value;
            var desc = "brak";
            var url = "/tasks/newtask/" + title + "/" + desc + "/" + date;
            $http.post(url).then(function (response) {
                setUpTasks();
            });
        }

        function toggle(id) {
            var url = "/tasks/toggle/" + id;
            $http.post(url).then(function (response) {
                setUpTasks();
            });
        }

        function deleteTask(id) {
            var url = "/tasks/delete/" + id;
            $http.post(url).then(function (response) {
                setUpTasks();
            });
        }

        function setUpTasks() {
            var url = "tasks/getOwnerNames";
            $http.get(url).then(function (response) {
                vm.ownersJson = response.data;
            });

            url = "/tasks/show/" + dones.indexOf(1, 0) + "/" + times.indexOf(1, 0) + "/" + groups.indexOf(1, 0);
            $http.get(url).then(function (response) {
                vm.tasks = response.data;
            });
        }

        function setUpButtons() {
            for (var i = 0; i < dones.length; i++) {
                donesBtn[i].style.background = dones[i] === 1 ? '#28a745' : '#c5cbd0';
                donesBtn[i].style.color = dones[i] === 1 ? '#fff' : '#636363';
            }
            for (var i = 0; i < times.length; i++) {
                timesBtn[i].style.background = times[i] === 1 ? '#28a745' : '#c5cbd0';
                timesBtn[i].style.color = times[i] === 1 ? '#fff' : '#636363';
            }
            for (var i = 0; i < groups.length; i++) {
                groupsBtn[i].style.background = groups[i] === 1 ? '#28a745' : '#c5cbd0';
                groupsBtn[i].style.color = groups[i] === 1 ? '#fff' : '#636363';
            }
        }

        function setActive(done, time, group) {
            setDone(done);
            setTime(time);
            setGroup(group);
        }

        function setDone(done) {
            for (var i = 0; i < dones.length; i++) {
                dones[i] = i === done ? 1 : 0;
            }
            setUpButtons();
            setUpTasks();
        }

        function setTime(time) {
            for (var i = 0; i < times.length; i++) {
                times[i] = i === time ? 1 : 0;
            }
            setUpButtons();
            setUpTasks();
        }

        function setGroup(group) {
            for (var i = 0; i < groups.length; i++) {
                groups[i] = i === group ? 1 : 0;
            }
            setUpButtons();
            setUpTasks();
        }
    }
})();
